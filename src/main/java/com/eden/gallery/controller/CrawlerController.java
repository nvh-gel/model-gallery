package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.eden.gallery.utils.UserRole.ROLE_ADMIN;
import static com.eden.gallery.utils.UserRole.ROLE_MODERATOR;

/**
 * Controller for managing crawled data.
 */
@RestController
@RequestMapping("/crawl")
@RolesAllowed({ROLE_ADMIN, ROLE_MODERATOR})
@AllArgsConstructor
public class CrawlerController {

    private final PageConverter<ModelDataVM> pageConverter = new PageConverter<>();
    private ModelCrawlService modelCrawlService;

    /**
     * Get a list of crawled data for models.
     *
     * @return list of model data
     */
    @GetMapping("/model")
    public ResponseModel getCrawledModel() {
        return ResponseModel.ok(modelCrawlService.findAll(1, 20));
    }

    @GetMapping("/model/top/{top}")
    public ResponseModel findTopModels(@PathVariable int top) {
        return ResponseModel.ok(modelCrawlService.findTopModels(top));
    }

    /**
     * Get a list of crawled model data by input page and page size.
     *
     * @param page page number
     * @param size page size
     * @return list of model data
     */
    @GetMapping("/model/{page}/{size}")
    public ResponseModel getCrawledModel(@PathVariable int page, @PathVariable int size) {
        return pageConverter.toResponseFromIterable(modelCrawlService.findAll(page, size));
    }

    /**
     * Search crawled model by name.
     *
     * @param page page number
     * @param size page size
     * @param name name to search
     * @return list of model data
     */
    @GetMapping("/search/{page}/{size}")
    public ResponseModel getCrawledModelsByName(@PathVariable int page,
                                                @PathVariable int size,
                                                @RequestParam(value = "name", required = false) String name) {
        return pageConverter.toResponseFromIterable(modelCrawlService.findByName(name, page, size));
    }

    /**
     * Update a model.
     *
     * @param request model data to update
     * @return update result
     */
    @PutMapping("/model")
    public ResponseModel update(@RequestBody ModelDataVM request) {
        return ResponseModel.updated(modelCrawlService.updateOnQueue(request));
    }

    /**
     * Skip a crawled model record.
     *
     * @param id model id
     * @return response of update
     */
    @PutMapping("/model/skip/{id}")
    public ResponseModel skipModel(@PathVariable String id) {
        return ResponseModel.updated(modelCrawlService.skipModel(id));
    }

    /**
     * Move a crawl model to model entity.
     *
     * @param request model data
     * @return creation result
     */
    @PostMapping("/model/move")
    public ResponseModel moveModel(@RequestBody ModelVM request) {
        return ResponseModel.created(modelCrawlService.moveModelData(request));
    }

    /**
     * Link an existing model entity with crawled model data.
     *
     * @param modelId  sql model id
     * @param objectId mongo model data id
     * @return update result
     */
    @PostMapping("/model/link")
    public ResponseModel linkModel(@RequestParam("modelId") Long modelId,
                                   @RequestParam("objectId") String objectId) {
        return ResponseModel.updated(modelCrawlService.linkModel(modelId, objectId));
    }
}
