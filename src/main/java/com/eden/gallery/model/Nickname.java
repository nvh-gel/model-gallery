package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Nickname entity.
 */
@Entity
@Getter
@Setter
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update nickname set is_deleted=true,updated_at=CURRENT_TIMESTAMP where id=?")
public class Nickname extends BaseModel {

    private String nick;
    private String url;

    @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Model model;
}
