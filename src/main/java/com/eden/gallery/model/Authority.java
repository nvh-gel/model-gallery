package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "authorities")
@Data
@EqualsAndHashCode(callSuper = true)
public class Authority extends BaseModel {

    @Column(name = "authority")
    private String authorities;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username",
            referencedColumnName = "username",
            foreignKey = @ForeignKey(name = "fk_authority_user"))
    private User user;
}
