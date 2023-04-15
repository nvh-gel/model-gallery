package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModelVM extends BaseVM {

    private String name;
    private String localName;
    private Integer yearOfBirth;
    private LocalDate dateOfBirth;
}
