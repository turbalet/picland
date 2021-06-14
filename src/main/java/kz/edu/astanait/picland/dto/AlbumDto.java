package kz.edu.astanait.picland.dto;

import kz.edu.astanait.picland.model.Pica;
import lombok.Data;

import java.util.List;

@Data
public class AlbumDto {

    private Long albumId;

    private String albumName;

    private String albumDescription;

    private String albumCover;

    private boolean isPrivate;

    //private List<Pica> picas;

}
