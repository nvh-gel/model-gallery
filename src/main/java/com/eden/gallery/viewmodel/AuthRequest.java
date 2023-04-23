package com.eden.gallery.viewmodel;

import lombok.Data;

/**
 * DTO for sign in.
 */
@Data
public class AuthRequest {

    private String username;
    private String password;
}
