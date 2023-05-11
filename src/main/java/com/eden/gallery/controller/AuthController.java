package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.service.AuthService;
import com.eden.gallery.viewmodel.AuthRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseModel login(@RequestBody AuthRequest request) {
        try {
            return ResponseModel.ok(authService.login(request));
        } catch (BadCredentialsException ex) {
            return ResponseModel.unauthorized();
        }
    }
}
