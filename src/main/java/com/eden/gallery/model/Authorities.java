package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data model for authority.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Authorities extends BaseModel {

    private String authority;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username",
            referencedColumnName = "username",
            foreignKey = @ForeignKey(name = "fk_authority_user"))
    private User user;
}
