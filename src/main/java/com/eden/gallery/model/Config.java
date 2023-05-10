package com.eden.gallery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Config data entity.
 */
@Document("config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Config {

    @Id
    private String id;
    private String value;
}
