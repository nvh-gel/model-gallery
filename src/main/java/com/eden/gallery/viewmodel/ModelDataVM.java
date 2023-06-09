package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import com.eden.gallery.utils.ModelTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for crawled model data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModelDataVM extends BaseVM {

    private String name;
    private String url;
    private Set<String> images;
    private Set<ModelTag> rel;
    private Integer fc;
    private Integer bb;
    private Integer wa;
    private Integer hi;
    private Integer bd;
    private Integer sx;
    private Integer ct;
    private Float avg;
    private Integer numberOfAlbum;
    private Boolean moved;
}
