package com.eden.gallery.service;

import com.eden.common.service.BaseService;
import com.eden.gallery.viewmodel.NicknameVM;

import java.util.List;

/**
 * Service for nickname handling.
 */
public interface NicknameService extends BaseService<NicknameVM> {

    /**
     * Create nicknames from a list of request.
     *
     * @param nicknameVMS nicknames to create
     * @return list of created nickname
     */
    List<NicknameVM> create(List<NicknameVM> nicknameVMS);
}
