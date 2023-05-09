package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import com.eden.gallery.security.oauth2.AuthProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * DTO for user.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVM extends BaseVM {

    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private Boolean expired;
    private Boolean locked;
    private Boolean credentialExpired;
    private List<AuthorityVM> authorities;
    private String name;
    private String imageUrl;
    private AuthProvider provider;
    private String providerId;
}
