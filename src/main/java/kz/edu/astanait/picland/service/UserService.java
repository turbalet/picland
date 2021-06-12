package kz.edu.astanait.picland.service;

import kz.edu.astanait.picland.bucket.BucketName;
import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.filestore.FileStore;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileStore fileStore;


    public User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
    }


    public void uploadUserProfileImage(Long userId, MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() +  " ]");
        }
        if(Arrays.asList(IMAGE_JPEG, IMAGE_GIF, IMAGE_PNG).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(User.class, "id", userId.toString())
        );

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setImgPath(filename);
            userRepository.save(user);
        } catch (IOException e){
            throw new IllegalStateException(e);
        }



    }

    public byte[] downloadUserProfileImage(Long userId) {
        User user = userRepository.getOne(userId);

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserId());

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }
}
