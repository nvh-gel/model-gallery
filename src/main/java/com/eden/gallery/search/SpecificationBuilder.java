package com.eden.gallery.search;

import com.eden.data.model.BaseModel;
import org.springframework.data.jpa.domain.Specification;

/**
 * Builder for criteria specification.
 *
 * @param <T> model for data
 */
public abstract class SpecificationBuilder<T extends BaseModel> {

    protected Specification<T> spec;

    /**
     * Constructor.
     */
    protected SpecificationBuilder() {
        this.spec = defaultSpec();
    }

    /**
     * Build a specification.
     *
     * @return specification.
     */
    public abstract Specification<T> build();

    /**
     * Build a default spec that specify object exists.
     *
     * @return basic specification
     */
    private Specification<T> defaultSpec() {

        return (t, cq, cb) -> t.isNotNull();
    }
}
