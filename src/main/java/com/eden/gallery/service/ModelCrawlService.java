package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;
import org.springframework.data.domain.Page;

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

    /**
     * Find a list of model data by name and paging.
     *
     * @param name name to find
     * @param page page number
     * @param size page size
     * @return list of found model
     */
    Page<ModelDataVM> findByName(String name, int page, int size);
}
