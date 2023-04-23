package com.eden.gallery.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for sign in response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String username;
    private String token;
}
