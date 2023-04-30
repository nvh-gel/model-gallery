package com.eden.gallery.search.impl;

import com.eden.gallery.model.Model;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.search.SpecificationBuilder;
import com.eden.gallery.utils.ModelCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import static com.eden.gallery.utils.Strings.LIKE_PATTERN;

/**
 * Spec builder for model criteria.
 */
public class ModelSpecificationBuilder extends SpecificationBuilder<Model> {

    /**
     * Constructor.
     */
    @SuppressWarnings("unused")
    public ModelSpecificationBuilder() {
        super();
    }

    /**
     * Constructor.
     *
     * @param criteria model criteria to init
     */
    public ModelSpecificationBuilder(ModelCriteria criteria) {
        super();
        this.fromCriteria(criteria);
    }

    /**
     * Set the criteria for spec.
     *
     * @param criteria search criteria
     */
    private void fromCriteria(@NonNull ModelCriteria criteria) {

        if (StringUtils.hasText(criteria.getName())) {
            hasName(criteria.getName());
        }
    }

    /**
     * Set the specification for search by name criteria.
     *
     * @param name name to search
     */
    public void hasName(String name) {

        String trimmed = name.trim().toLowerCase();
        Specification<Model> nameLike = (model, cq, cb) ->
                cb.like(cb.lower(model.get("name")), LIKE_PATTERN.formatted(trimmed));
        Specification<Model> localNameLike = (model, cq, cb) ->
                cb.like(cb.lower(model.get("nativeName")), LIKE_PATTERN.formatted(trimmed));
        Specification<Model> nickNameLike = (model, cq, cb) -> {
            Join<Model, Nickname> modelNickname = model.join("nicknames", JoinType.LEFT);
            return cb.like(cb.lower(modelNickname.get("nick")), LIKE_PATTERN.formatted(trimmed));
        };
        this.spec = this.spec.and(nameLike.or(localNameLike).or(nickNameLike));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Specification<Model> build() {
        return Specification.where(this.spec);
    }
}
