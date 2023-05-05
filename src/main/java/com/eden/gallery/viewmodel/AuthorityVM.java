package com.eden.gallery.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authority.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityVM {

    private String username;
    private String authority;
    private String defaultUrl;
    private Integer level;
    private String pages;
}
