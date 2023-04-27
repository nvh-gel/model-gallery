package com.eden.gallery.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Config data entity.
 */
@Document("config")
@Data
public class Config {

    @Id
    private String id;
    private String value;
}
