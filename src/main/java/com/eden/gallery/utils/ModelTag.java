package com.eden.gallery.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Inner class for tags related to model data.
 */
@Getter
@Setter
public class ModelTag {

    private String name;
    private String url;
    private Boolean isPublisher;
    private Boolean isCategory;
}
