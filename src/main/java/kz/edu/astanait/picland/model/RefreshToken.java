package kz.edu.astanait.picland.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "created_date")
    private Instant createdDate;

}