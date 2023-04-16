package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update model set is_deleted=true, updated_at=CURRENT_TIMESTAMP where id=?")
public class Model extends BaseModel {

    private String name;
    private String localName;
    private Integer yearOfBirth;
    private LocalDate dateOfBirth;
    private String thumbnail;
    private String url;

    @OneToMany(mappedBy = "model", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Nickname> nicknames;
}
