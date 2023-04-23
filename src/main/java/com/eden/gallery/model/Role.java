package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Data model for user role.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update role set is_deleted=true,updated_at=CURRENT_TIMESTAMP where id=?")
public class Role extends BaseModel {

    private String name;
}
