package kz.edu.astanait.picland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "pica_albums",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "pica_id"))


    private Set<Pica> picas = new HashSet<>();
}
