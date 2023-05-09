package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * Data model for authority.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Authorities extends BaseModel implements GrantedAuthority {

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "authority",
            referencedColumnName = "name",
            foreignKey = @ForeignKey(name = "fk_authority_role")
    )
    private transient Role auth;

    @Column(name = "authority")
    private String authority;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username",
            referencedColumnName = "username",
            foreignKey = @ForeignKey(name = "fk_authority_user"))
    private User user;

    /**
     * Return authority string for security.
     *
     * @return role string
     */
    @Override
    public String getAuthority() {
        return authority;
    }

    /**
     * Return default role url for user.
     *
     * @return string of default url
     */
    public String getDefaultUrl() {
        return auth.getDefaultUrl();
    }

    /**
     * Return calculated user access level.
     *
     * @return role access level
     */
    public Integer getLevel() {
        return auth.getLevel();
    }

    /**
     * Return role configured pages.
     *
     * @return string list of page
     */
    public String getPages() {
        return auth.getPages();
    }
}
