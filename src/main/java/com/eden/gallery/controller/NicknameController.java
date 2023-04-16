package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for managing nickname.
 */
@RestController
@RequestMapping("/nick")
public class NicknameController {

    private NicknameService nicknameService;

    /**
     * Create a nickname for model.
     *
     * @param request nick creation request
     * @return creation response
     */
    @PostMapping
    public ResponseModel createNick(@RequestBody NicknameVM request) {

        return ResponseModel.created(nicknameService.create(request));
    }

    /**
     * Setter
     */
    @Autowired
    public void setNicknameService(NicknameService nicknameService) {
        this.nicknameService = nicknameService;
    }
}
