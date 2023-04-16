package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public ResponseModel home() {

        return ResponseModel.ok("Model Gallery API");
    }

    @GetMapping("/healthz")
    public ResponseModel health() {

        return ResponseModel.ok("UP");
    }
}
