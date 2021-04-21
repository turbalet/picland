package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.model.Album;
import kz.edu.astanait.picland.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Album> getAllAlbums(){
        return albumService.findAllAlbums();
    }

    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable("id") Long id){
        return albumService.findAlbum(id);
    }

    @GetMapping("/user/{userId}")
    public List<Album> getUserAlbums(@PathVariable("userId") Long userId, Principal principal){
        return albumService.findUserAlbums(userId, principal);
    }

    @PostMapping("")
    public ResponseEntity<?> createAlbum(@RequestBody Album album){
        try{
            albumService.saveAlbum(album);
            return ResponseEntity.ok(album);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateAlbum(@RequestBody Album album){
        try{
            albumService.updateAlbum(album);
            return ResponseEntity.ok(album);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("#ownerId == principal.getUser().userId")
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
