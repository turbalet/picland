package kz.edu.astanait.picland.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    @Column(name = "interest_name")
    private String interestName;

    @Column(name = "interest_cover")
    private String interestCover;

    @Column(name = "parent_interest_id")
    private Long parentInterestId;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "interests")
    private Set<User> users = new HashSet<>();

    @OneToMany(targetEntity = Pica.class, fetch = FetchType.LAZY, mappedBy = "interest")
    @JsonIgnore
    private Set<Pica> picas = new HashSet<>();
}
