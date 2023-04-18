package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.AccountVM;

/**
 * Account business services.
 */
public interface AccountService extends BaseService<AccountVM> {

    String activateAccount(Long id);

    String deactivateAccount(Long id);

    String verifyAccount(Long id, String token);
}
