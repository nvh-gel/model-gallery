package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home controller for general information.
 */
@RestController
@RequestMapping("/")
public class RootController {

    /**
     * Basic information of api.
     *
     * @return response of info
     */
    @GetMapping
    public ResponseModel home() {

        return ResponseModel.ok("Model Gallery API");
    }

    /**
     * Basic status of api.
     *
     * @return response of UP
     */
    @GetMapping("/healthz")
    public ResponseModel health() {

        return ResponseModel.ok("UP");
    }
}
