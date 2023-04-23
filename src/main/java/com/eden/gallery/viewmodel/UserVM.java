package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
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
    private List<AuthorityVM> authorities;
}
