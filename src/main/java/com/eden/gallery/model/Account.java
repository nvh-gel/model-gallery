package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Data entity for account.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update account set is_deleted=true,updated_at=CURRENT_TIMESTAMP where id=?")
public class Account extends BaseModel {

    private String username;
    private String password;
    private boolean isActive = true;
    private boolean isVerified = false;
    private String email;
    private String token;
    private String verifyToken;
}
