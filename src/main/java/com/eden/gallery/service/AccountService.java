package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.AccountVM;

/**
 * Account business services.
 */
public interface AccountService extends BaseService<AccountVM> {

    /**
     * Activate an account by id.
     *
     * @param id account id
     * @return activation transaction uuid
     */
    String activateAccount(Long id);

    /**
     * Deactivate an account by id.
     *
     * @param id account id
     * @return deactivation transaction uuid
     */
    String deactivateAccount(Long id);

    /**
     * Verify an account by id.
     *
     * @param id    account id to verify
     * @param token verification token
     * @return null if token fail, else verification transaction id
     */
    String verifyAccount(Long id, String token);
}
