package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.dto.ProfileDto;
import kz.edu.astanait.picland.model.Album;
import kz.edu.astanait.picland.model.Follower;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.service.AlbumService;
import kz.edu.astanait.picland.service.FollowerService;
import kz.edu.astanait.picland.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final UserService userService;

    private final AlbumService albumService;

    private final FollowerService followerService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable("username") String username, Principal principal){
        try{
            User user = userService.findUserByUsername(username);
            List<Album> albums = albumService.findUserAlbums(user.getUserId(), principal);
            long followerCount = followerService.getUserFollowersCount(user.getUserId());
            long followCount = followerService.getUserFollowCount(user.getUserId());
            return ResponseEntity.ok(new ProfileDto(user, albums, followerCount, followCount));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/image/upload"
    )
    public void uploadUserProfileImage(Principal principal,
                                       @RequestParam("file")MultipartFile file){
        userService.uploadUserProfileImage(userService.findUserByUsername(principal.getName()).getUserId(), file);
    }


    @GetMapping("{userProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileId") Long userProfileId) {
        return userService.downloadUserProfileImage(userProfileId);
    }

}
