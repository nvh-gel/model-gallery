package com.eden.gallery.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for sign in response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String username;
    private String token;
    private Integer level;
    private String defaultUrl;
    private List<AuthorityVM> authorities;
}
