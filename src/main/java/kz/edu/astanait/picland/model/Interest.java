package kz.edu.astanait.picland.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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

    @ManyToMany(mappedBy = "interests")
    Set<User> users;

}
