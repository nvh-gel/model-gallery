package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import lombok.AllArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home controller for general information.
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RootController {

    private BuildProperties buildProperties;

    /**
     * Basic information of api.
     *
     * @return response of info
     */
    @GetMapping
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> home() {
        return ResponseEntity.ofNullable("Model Gallery API - versions %s".formatted(buildProperties.getVersion()));
    }

    /**
     * Basic status of api.
     *
     * @return response of UP
     */
    @GetMapping("/healthz")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> health() {
        return ResponseEntity.ofNullable("UP");
    }
}
