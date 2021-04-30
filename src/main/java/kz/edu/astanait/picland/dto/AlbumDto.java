package kz.edu.astanait.picland.dto;

import lombok.Data;

@Data
public class AlbumDto {

    private Long albumId;

    private String albumName;

    private String albumDescription;

    private String albumCover;

    private boolean isPrivate;

}
