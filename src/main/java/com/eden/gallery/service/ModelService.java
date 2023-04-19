package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.viewmodel.ModelVM;
import org.springframework.data.domain.Page;

/**
 * Service for model handling.
 */
public interface ModelService extends BaseService<ModelVM> {

    /**
     * Search model by requested criteria.
     *
     * @param request request data
     * @return a page of model data
     */
    Page<ModelVM> searchModel(SearchRequest<ModelCriteria> request);
}
