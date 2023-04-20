package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for authority.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityVM extends BaseVM {

    private String username;
    private String authorities;
}
