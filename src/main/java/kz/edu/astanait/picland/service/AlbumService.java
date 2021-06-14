package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.dto.AlbumDto;
import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.exception.ForbiddenException;
import kz.edu.astanait.picland.model.Album;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.AlbumRepository;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;

    public List<Album> findAllAlbums(){
        return albumRepository.findAll();
    }

    public Album findAlbum(Long albumId, Principal principal){

         Album album = albumRepository.findById(albumId).orElse(null);
         if(album != null){
             if(album.isPrivate()){
                 if(album.getUser().getUsername().equals(principal.getName())){
                     return album;
                 }
                 throw new ForbiddenException();
             }
             return album;
         } else{
             throw new EntityNotFoundException(Album.class, "id", albumId.toString());
         }
    }

    public List<Album> findUserAlbums(Long userId, Principal principal){
        User currentUser = userRepository.findUserByUsername(principal.getName()).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            if(user.getUserId().equals(currentUser.getUserId())){
                return albumRepository.getAlbumsByUserUserId(userId);
            }
            return albumRepository.getAlbumsByUserUserIdAndIsPrivateFalse(userId);
        }
        else {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }
    }

    public List<Album> findUserAlbums(String username, Principal principal){
        User currentUser = userRepository.findUserByUsername(principal.getName()).orElse(null);
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user != null){
            if(user.getUserId().equals(currentUser.getUserId())){
                return albumRepository.getAlbumsByUserUsername(username);
            }
            return albumRepository.getAlbumsByUserUserIdAndIsPrivateFalse(user.getUserId());
        }
        else {
            throw new EntityNotFoundException(User.class, "username", username);
        }
    }

    public void saveAlbum(Album album){
        albumRepository.save(album);
    }

    public Album updateAlbum(Album album){
        return albumRepository.save(album);
    }

    public void deleteAlbum(Long albumId){
        albumRepository.deleteById(albumId);
    }

    public void createAlbum(String username, MultipartFile file, String albumName) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Album album = new Album();
        album.setAlbumName(albumName);
        album.setAlbumCover("" + fileName);
        album.setUser(userRepository.findUserByUsername(username).orElse(null));
        album.setPrivate(false);
        albumRepository.save(album);
        String uploadDir = "C:\\Users\\acer\\IdeaProjects\\picland\\src\\main\\resources\\static\\images";
        saveFile(uploadDir, fileName, file);
    }

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
