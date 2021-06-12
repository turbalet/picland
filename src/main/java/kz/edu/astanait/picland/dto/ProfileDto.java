package kz.edu.astanait.picland.dto;

import kz.edu.astanait.picland.model.Album;
import kz.edu.astanait.picland.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private User user;

    private List<Album> albums;

    private long followerCount;

    private long followCount;
}
