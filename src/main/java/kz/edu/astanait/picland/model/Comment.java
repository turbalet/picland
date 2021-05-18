package kz.edu.astanait.picland.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "message")
    private String message;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "pica_id")
    private Pica pica;
}
