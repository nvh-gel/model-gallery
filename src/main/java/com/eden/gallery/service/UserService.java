package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.UserVM;

/**
 * Service for user handling.
 */
public interface UserService extends BaseService<UserVM> {

    UserVM create(UserVM request, String... roles);

    String createOnQueue(UserVM request, String... roles);
}
