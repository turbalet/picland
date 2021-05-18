package kz.edu.astanait.picland.dto;

import kz.edu.astanait.picland.model.Pica;
import kz.edu.astanait.picland.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CommentDto {

    private Long commentId;

    @NotBlank
    private String message;

    private Date comment_date;

    private User user;

    private Pica pica;

}
