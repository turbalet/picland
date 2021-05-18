package kz.edu.astanait.picland.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "picas")
public class Pica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picaId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "picas")
    private Set<Album> albums = new HashSet<>();

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pica")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();
}
