package com.eden.gallery.viewmodel;

import com.eden.common.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for account.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountVM extends BaseVM {

    private String username;
    private String password;
    private Boolean isActive;
    private Boolean isVerified;
    private String email;
}
