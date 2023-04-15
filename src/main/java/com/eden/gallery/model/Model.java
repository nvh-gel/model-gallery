package com.eden.gallery.model;

import com.eden.data.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Model extends BaseModel {

    private String name;
    private String localName;
    private Integer yearOfBirth;
    private LocalDate dateOfBirth;
}
