package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private ModelCrawlService modelCrawlService;

    /**
     * Get a list of crawled data for models.
     *
     * @return list of model data
     */
    @GetMapping("/model")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getCrawledModel() {
        return ResponseEntity.ofNullable(modelCrawlService.findAll(1, 20));
    }

    /**
     * Get top model by score.
     *
     * @param top number of model to retrieve.
     * @return list of top model data
     */
    @GetMapping("/model/top/{top}")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> findTopModels(@PathVariable int top) {
        return ResponseEntity.ofNullable(modelCrawlService.findTopModels(top));
    }

    /**
     * Get a list of crawled model data by input page and page size.
     *
     * @param page page number
     * @param size page size
     * @return list of model data
     */
    @GetMapping("/model/{page}/{size}")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getCrawledModel(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ofNullable(modelCrawlService.findAll(page, size));
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
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getCrawledModelsByName(@PathVariable int page,
                                                         @PathVariable int size,
                                                         @RequestParam(value = "name", required = false) String name) {
        return ResponseEntity.ofNullable(modelCrawlService.findByName(name, page, size));
    }

    /**
     * Update a model.
     *
     * @param request model data to update
     * @return update result
     */
    @PutMapping("/model")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> update(@RequestBody ModelDataVM request) {
        return ResponseEntity.ofNullable(modelCrawlService.updateOnQueue(request));
    }

    /**
     * Skip a crawled model record.
     *
     * @param id model id
     * @return response of update
     */
    @PutMapping("/model/skip/{id}")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> skipModel(@PathVariable String id) {
        return ResponseEntity.ofNullable(modelCrawlService.skipModel(id));
    }

    /**
     * Move a crawl model to model entity.
     *
     * @param request model data
     * @return creation result
     */
    @PostMapping("/model/move")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> moveModel(@RequestBody ModelVM request) {
        return ResponseEntity.ofNullable(modelCrawlService.moveModelData(request));
    }

    /**
     * Link an existing model entity with crawled model data.
     *
     * @param modelId  sql model id
     * @param objectId mongo model data id
     * @return update result
     */
    @PostMapping("/model/link")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> linkModel(@RequestParam("modelId") Long modelId,
                                            @RequestParam("objectId") String objectId) {
        return ResponseEntity.ofNullable(modelCrawlService.linkModel(modelId, objectId));
    }
}
