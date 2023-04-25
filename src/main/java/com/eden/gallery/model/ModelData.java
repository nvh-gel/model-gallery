package com.eden.gallery.model;

import com.eden.nosql.model.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

/**
 * MongoDB document for crawled model data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document("model")
public class ModelData extends BaseDocument {

    private String name;
    private String url;
    private List<String> images;
    private Set<String> rel;
    private Integer fc;
    private Integer bb;
    private Integer wa;
    private Integer hi;
    private Integer bd;
    private Integer sx;
    private Integer ct;
    private Float avg;
    private Integer numberOfAlbum;
    private Boolean skip;
    private Boolean needCrawl = true;
    private Boolean moved;
}
