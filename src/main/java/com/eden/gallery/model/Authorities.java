package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Data model for authority.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Authorities extends BaseModel implements GrantedAuthority {

    private String authority;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username",
            referencedColumnName = "username",
            foreignKey = @ForeignKey(name = "fk_authority_user"))
    private User user;
}
