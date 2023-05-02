package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for model.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelVM extends BaseVM {

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

    private List<NicknameVM> nicknames;
}
