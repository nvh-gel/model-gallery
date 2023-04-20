package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity(name = "users")
@Table(uniqueConstraints = {@UniqueConstraint(name = "con_unique_username", columnNames = "username")})
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel {

    @Column(unique = true)
    String username;
    String password;
    boolean enabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Authority> authorities;
}
