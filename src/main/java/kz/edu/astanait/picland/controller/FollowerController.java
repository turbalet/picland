package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.exception.EntityNotFoundException;
import kz.edu.astanait.picland.model.Follower;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.service.FollowerService;
import kz.edu.astanait.picland.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/followers")
public class FollowerController {

    private final FollowerService followerService;

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public List<Follower> getUserFollowers(@PathVariable("userId") Long userId){
        return followerService.findUserFollowers(userId);
    }

    @GetMapping("/follow/user/{userId}")
    public ResponseEntity<?> doFollow(@PathVariable("userId") Long userId, Principal principal){
        try{
            User currentUser = userService.findUserByUsername(principal.getName());
            followerService.doFollow(userId, currentUser);
            return ResponseEntity.ok("Successfully followed");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/unfollow/user/{userId}")
    public ResponseEntity<?> doUnFollow(@PathVariable("userId") Long userId, Principal principal){
        try{
            User currentUser = userService.findUserByUsername(principal.getName());
            followerService.doUnFollow(userId, currentUser.getUserId());
            return ResponseEntity.ok("Successfully unfollowed");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
