package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Nickname entity.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Nickname extends BaseModel {

    private String nick;

    @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Model model;
}
