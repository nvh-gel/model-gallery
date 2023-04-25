package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eden.gallery.security.Role.ROLE_ADMIN;
import static com.eden.gallery.security.Role.ROLE_MODERATOR;

/**
 * Rest controller for managing model.
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    private ModelService modelService;
    private final PageConverter<ModelVM> pageConverter = new PageConverter<>();

    /**
     * Create model from request.
     *
     * @param request model request data
     * @return created response
     */
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
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

        return pageConverter.toResponseFromIterable(modelService.findAll(1, 10));
    }

    /**
     * Get a list of models by page and size.
     *
     * @param page page number
     * @param size page size
     * @return list of models
     */
    @GetMapping("/{page}/{size}")
    public ResponseModel getModelsByPage(@PathVariable Integer page, @PathVariable Integer size) {

        return pageConverter.toResponseFromIterable(modelService.findAll(page, size));
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
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
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
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
    @DeleteMapping("/{id}")
    public ResponseModel deleteModel(@PathVariable Long id) {

        return ResponseModel.deleted(modelService.deleteOnQueue(id));
    }

    /**
     * Search model by criteria.
     *
     * @param request search criteria and paging.
     * @return response of found models matched the criteria
     */
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
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
}
