package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import com.eden.gallery.security.oauth2.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Data model for user.
 */
@Entity(name = "users")
@Table(uniqueConstraints = {@UniqueConstraint(name = "con_unique_username", columnNames = "username")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update users set is_deleted=true,updated_at=CURRENT_TIMESTAMP where id=?")
public class User extends BaseModel implements UserDetails {

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private boolean enabled = true;
    private boolean expired = false;
    private boolean locked = false;
    private boolean credentialExpired = false;

    private String name;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authorities> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }
}
