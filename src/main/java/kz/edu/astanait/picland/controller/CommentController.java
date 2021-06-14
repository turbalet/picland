package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.dto.CommentDto;
import kz.edu.astanait.picland.model.Comment;
import kz.edu.astanait.picland.service.CommentService;
import kz.edu.astanait.picland.service.PicaService;
import kz.edu.astanait.picland.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final PicaService picaService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Comment> getAllComments(){
        return commentService.findAllComments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Comment getComment(@PathVariable("id") Long id){
        return commentService.findComment(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/pica/{picaId}")
    public List<Comment> getPicaComments(@PathVariable("picaId") Long picaId){
        return commentService.findPicaComments(picaId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/pica/{picaId}")
    public ResponseEntity<?> writeComment(@PathVariable("picaId") Long picaId,
                                       @RequestBody String message,
                                       Principal principal){
        try{
            Comment comment = new Comment();
            comment.setMessage(message);
            comment.setUser(userService.findUserByUsername(principal.getName()));
            comment.setPica(picaService.findPica(picaId));
            comment.setCommentDate(new Date());
            commentService.saveComment(comment);
            return ResponseEntity.ok(comment);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or #userId==principal.getUser().userId")
    @PutMapping("/{commentId}/user/{userId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,
                                           @PathVariable("userId") Long userId,
                                           @RequestBody CommentDto commentDto){
        try{
            Comment comment = commentService.findComment(commentId);
            comment.setMessage(commentDto.getMessage());
            commentService.updateComment(comment);
            return ResponseEntity.ok(comment);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or #userId==principal.getUser().userId")
    @DeleteMapping("/{commentId}/user/{userId}")
    public ResponseEntity<?> removeComment(@PathVariable("commentId") Long commentId,
                                           @PathVariable("userId") Long userId){
        try{
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment was successfully deleted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
