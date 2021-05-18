package kz.edu.astanait.picland.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
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
