package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for managing model.
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    private ModelService modelService;
    private PageConverter pageConverter;

    /**
     * Create model from request.
     *
     * @param request model request data
     * @return created response
     */
    @PostMapping
    public ResponseModel createModel(@RequestBody ModelVM request) {

        return ResponseModel.created(modelService.createOnQueue(request));
    }

    /**
     * Get all models.
     *
     * @return list of models
     */
    @GetMapping
    public ResponseModel getModels() {

        return ResponseModel.ok(modelService.findAll());
    }

    /**
     * Get a single model by id.
     *
     * @param id model id
     * @return response of found model
     */
    @GetMapping("/{id}")
    public ResponseModel getModelById(@PathVariable Long id) {

        return ResponseModel.ok(modelService.findById(id));
    }

    /**
     * Update a model.
     *
     * @param request model updating request
     * @return updated response
     */
    @PutMapping
    public ResponseModel updateModel(@RequestBody ModelVM request) {

        return ResponseModel.updated(modelService.updateOnQueue(request));
    }

    /**
     * Delete a model by id.
     *
     * @param id model id
     * @return deleted response
     */
    @DeleteMapping("/{id}")
    public ResponseModel deleteModel(@PathVariable Long id) {

        return ResponseModel.deleted(modelService.delete(id));
    }

    /**
     * Search model by criteria.
     *
     * @param request search criteria and paging.
     * @return response of found models matched the criteria
     */
    @GetMapping("/search")
    public ResponseModel searchModel(@RequestBody SearchRequest<ModelCriteria> request) {

        return pageConverter.toResponseFromPaging(modelService.searchModel(request));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setPageConverter(PageConverter pageConverter) {
        this.pageConverter = pageConverter;
    }
}
