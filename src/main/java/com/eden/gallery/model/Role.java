package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import com.eden.gallery.utils.Constants;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

/**
 * Data model for user role.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update role set is_deleted=true,updated_at=CURRENT_TIMESTAMP where id=?")
public class Role extends BaseModel implements Serializable {

    private String name;
    private String defaultUrl;
    private int level;
    private String pages;

    /**
     * Return default static role for USER.
     *
     * @return role USER
     */
    public static Role user() {
        return new Role(Constants.ROLE_USER, null, 1, null);
    }
}
