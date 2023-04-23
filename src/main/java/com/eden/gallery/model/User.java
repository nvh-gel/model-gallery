package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Data model for user.
 */
@Entity(name = "users")
@Table(uniqueConstraints = {@UniqueConstraint(name = "con_unique_username", columnNames = "username")})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel implements UserDetails {

    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authorities> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
