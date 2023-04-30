package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

/**
 * Model entity.
 */
@Entity
@Getter
@Setter
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update model set is_deleted=true, updated_at=CURRENT_TIMESTAMP where id=?")
public class Model extends BaseModel {

    private String name;
    private String nativeName;
    private String url;
    private String thumbnail;
    private LocalDate dateOfBirth;
    private Integer yearOfBirth;
    private Integer boob;
    private Integer waist;
    private Integer hip;
    private String description;

    @OneToMany(mappedBy = "model", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Nickname> nicknames;
}
