package kz.edu.astanait.picland.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private long roleId;

    private String name;

    public Role(){}

    public Role(String name){
        this.name = name;
    }
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users = new HashSet<>();

}
