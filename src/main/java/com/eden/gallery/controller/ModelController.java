package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eden.gallery.utils.UserRole.ROLE_ADMIN;
import static com.eden.gallery.utils.UserRole.ROLE_MODERATOR;

/**
 * Rest controller for managing model.
 */
@RestController
@RequestMapping("/model")
@AllArgsConstructor
public class ModelController {

    private final PageConverter<ModelVM> pageConverter = new PageConverter<>();

    private ModelService modelService;

    /**
     * Create model from request.
     *
     * @param request model request data
     * @return created response
     */
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
    @PostMapping
    @LogExecutionTime
    public ResponseModel createModel(@RequestBody ModelVM request) {
        return ResponseModel.created(modelService.createOnQueue(request));
    }

    /**
     * Get all models.
     *
     * @return list of models
     */
    @GetMapping
    @LogExecutionTime
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
    @LogExecutionTime
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
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getModelById(@PathVariable Long id) {
        return ResponseEntity.ofNullable(modelService.findById(id));
    }

    /**
     * Update a model.
     *
     * @param request model updating request
     * @return updated response
     */
    @RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
    @PutMapping
    @LogExecutionTime
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
    @LogExecutionTime
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
    @PostMapping("/search")
    @LogExecutionTime
    public ResponseModel searchModel(@RequestBody SearchRequest<ModelCriteria> request) {
        return pageConverter.toResponseFromPaging(modelService.searchModel(request));
    }
}
