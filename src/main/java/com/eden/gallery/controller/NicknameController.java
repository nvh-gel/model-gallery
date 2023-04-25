package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

        return ResponseModel.created(nicknameService.createOnQueue(request));
    }

    /**
     * Update a nickname for model.
     *
     * @param request nick updating data
     * @return updating request
     */
    @PutMapping
    public ResponseModel updateNick(@RequestBody NicknameVM request) {

        return ResponseModel.updated(nicknameService.updateOnQueue(request));
    }

    /**
     * Delete a nickname by id.
     *
     * @param id nickname id to delete
     * @return deletion result
     */
    @DeleteMapping("/{id}")
    public ResponseModel deleteNick(@PathVariable Long id) {

        return ResponseModel.deleted(nicknameService.deleteOnQueue(id));
    }

    /**
     * Setter
     */
    @Autowired
    public void setNicknameService(NicknameService nicknameService) {
        this.nicknameService = nicknameService;
    }
}
