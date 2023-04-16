package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.viewmodel.ModelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
public class ModelController {

    private ModelService modelService;

    @PostMapping
    public ResponseModel createModel(@RequestBody ModelVM request) {

        return ResponseModel.created(modelService.create(request));
    }

    @GetMapping
    public ResponseModel getModels() {

        return ResponseModel.ok(modelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseModel getModelById(@PathVariable Long id) {

        return ResponseModel.ok(modelService.findById(id));
    }

    @PutMapping
    public ResponseModel updateModel(@RequestBody ModelVM request) {

        return ResponseModel.updated(modelService.update(request));
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
