package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.AuthService;
import com.eden.gallery.viewmodel.AuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    /**
     * Attempt log in.
     *
     * @param request log in request with username and password
     * @return authentication response with username and token
     */
    @PostMapping("/login")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ofNullable(authService.login(request));
    }
}
