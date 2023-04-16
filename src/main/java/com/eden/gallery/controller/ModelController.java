package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.ModelService;
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

    /**
     * Create model from request.
     *
     * @param request model request data
     * @return created response
     */
    @PostMapping
    public ResponseModel createModel(@RequestBody ModelVM request) {

        return ResponseModel.created(modelService.create(request));
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

        return ResponseModel.updated(modelService.update(request));
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
     * Setter.
     */
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
