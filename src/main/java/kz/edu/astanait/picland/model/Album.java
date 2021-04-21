package kz.edu.astanait.picland.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @Column(name = "album_description")
    private String albumDescription;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "album_cover")
    private String albumCover;

    @Column(name = "is_private")
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

}
