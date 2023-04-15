package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Model extends BaseModel {

    private String name;
    private String localName;
    private Integer yearOfBirth;
    private LocalDate dateOfBirth;
    private String thumbnail;
    private String url;

    @OneToMany(mappedBy = "model", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Nickname> nicknames;
}
