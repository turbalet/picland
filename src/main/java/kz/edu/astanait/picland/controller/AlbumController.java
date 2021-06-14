package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.dto.AlbumDto;
import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Album;
import kz.edu.astanait.picland.service.AlbumService;
import kz.edu.astanait.picland.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/albums")
@CrossOrigin
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Album> getAllAlbums(){
        return albumService.findAllAlbums();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable("id") Long id, Principal principal){
        return albumService.findAlbum(id, principal);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//    @GetMapping("/user/{userId}")
//    public List<Album> getUserAlbums(@PathVariable("userId") Long userId, Principal principal){
//        return albumService.findUserAlbums(userId, principal);
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user/{username}")
    public List<Album> getUserAlbums(@PathVariable("username") String username, Principal principal){
        return albumService.findUserAlbums(username, principal);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//    @PostMapping("")
//    public ResponseEntity<?> createAlbum(@RequestBody AlbumDto albumDto, Principal principal){
//        try{
//            Album album = modelMapper.map(albumDto, Album.class);
//            album.setUser(userService.findUserByUsername(principal.getName()));
//            albumService.saveAlbum(album);
//            return ResponseEntity.ok(album);
//        }
//        catch (EntityNotFoundException e){
//            throw e;
//        }
//        catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<?> updateAlbum(@RequestBody AlbumDto albumDto, Principal principal){
        try{
            Album album = modelMapper.map(albumDto, Album.class);
            album.setUser(userService.findUserByUsername(principal.getName()));
            albumService.updateAlbum(album);
            return ResponseEntity.ok(album);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping()
    public void newAlbum(Principal principal,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("albumName") String albumName) throws IOException {
        albumService.createAlbum(principal.getName(), file, albumName);
//        albumService.createAlbum(principal.getName(), file,modelMapper.map(albumDto, Album.class));
    }



    @PreAuthorize("#ownerId == principal.getUser().userId or hasRole('ADMIN')")
    @DeleteMapping("/remove/{id}/owner/{ownerId}")
    public ResponseEntity<?> removeAlbum(@PathVariable("id") Long id,
                                         @PathVariable("ownerId") Long ownerId){
        try{
            albumService.deleteAlbum(id);
            return ResponseEntity.ok("Album successfully removed");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
