package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.UserVM;

/**
 * Service for user handling.
 */
public interface UserService extends BaseService<UserVM> {

    /**
     * Create user with a list of roles.
     *
     * @param request user data
     * @param roles   array of roles
     * @return created user
     */
    UserVM create(UserVM request, String... roles);

    /**
     * Create a user with list roles on queue system.
     *
     * @param request user data
     * @param roles   arrays of roles
     * @return creation transaction uuid
     */
    String createOnQueue(UserVM request, String... roles);
}
