package com.eden.gallery.security.oauth2.user;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.Role;
import com.eden.gallery.model.User;
import com.eden.gallery.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

/**
 * User principle to handle returned OAuth2User.
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
    private transient Map<String, Object> attributes;

    /**
     * Create oauth user principal from User model.
     *
     * @param user user model.
     * @return user principal
     */
    public static UserPrincipal create(User user) {
        List<Authorities> authorities = List.of(new Authorities(Role.user(), Constants.ROLE_USER, user));
        return new UserPrincipal(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                null);
    }

    /**
     * Create oauth user principal from User model and attributes.
     *
     * @param user       user model
     * @param attributes oauth attributes
     * @return user principal
     */
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
