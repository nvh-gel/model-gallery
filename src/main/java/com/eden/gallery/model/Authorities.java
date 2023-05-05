package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Authorities extends BaseModel implements GrantedAuthority {

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "authority",
            referencedColumnName = "name",
            foreignKey = @ForeignKey(name = "fk_authority_role")
    )
    private Role authority;

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
        return authority.getName();
    }

    /**
     * Return default role url for user.
     *
     * @return string of default url
     */
    @SuppressWarnings("unused")
    public String getDefaultUrl() {
        return authority.getDefaultUrl();
    }

    public Integer getLevel() {
        return authority.getLevel();
    }

    public String getPages() {
        return authority.getPages();
    }
}
