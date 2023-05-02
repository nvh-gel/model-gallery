package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;

import java.util.List;

/**
 * Service for handling crawled model data
 */
public interface ModelCrawlService extends BaseService<ModelDataVM> {

    /**
     * Find a list of model order by average rating.
     *
     * @param top top number of models to get
     * @return list of model data
     */
    List<ModelDataVM> findTopModels(int top);

    /**
     * Mark a model data as skipped.
     *
     * @param id model id to skip
     * @return update result
     */
    String skipModel(String id);

    /**
     * Convert a crawled model to model entity.
     *
     * @param request request model
     * @return creation result
     */
    String moveModelData(ModelVM request);

    /**
     * Link an existing model entity with model data record.
     *
     * @param modelId  sql model id
     * @param objectId mongo model data id
     * @return update result
     */
    String linkModel(Long modelId, String objectId);
}
