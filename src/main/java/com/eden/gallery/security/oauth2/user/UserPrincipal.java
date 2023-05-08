package com.eden.gallery.security.oauth2.user;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.Role;
import com.eden.gallery.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String username, String email, String password, List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<Authorities> authorities = Collections.singletonList(new Authorities(Role.user(), user));
        return new UserPrincipal(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

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

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
