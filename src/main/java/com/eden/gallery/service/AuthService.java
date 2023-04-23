package com.eden.gallery.service;

import com.eden.gallery.viewmodel.AuthRequest;
import com.eden.gallery.viewmodel.AuthResponse;

/**
 * Service for authentication.
 */
public interface AuthService {

    /**
     * Attempt login from request.
     *
     * @param request login request with username and password
     * @return login response
     */
    AuthResponse login(AuthRequest request);
}
