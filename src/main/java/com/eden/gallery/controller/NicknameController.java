package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
public class NicknameController {

    private NicknameService nicknameService;

    /**
     * Create a nickname for model.
     *
     * @param request nick creation request
     * @return creation response
     */
    @PostMapping
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> createNick(@RequestBody NicknameVM request) {
        return ResponseEntity.ofNullable(nicknameService.createOnQueue(request));
    }

    /**
     * Update a nickname for model.
     *
     * @param request nick updating data
     * @return updating request
     */
    @PutMapping
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> updateNick(@RequestBody NicknameVM request) {
        return ResponseEntity.ofNullable(nicknameService.updateOnQueue(request));
    }

    /**
     * Delete a nickname by id.
     *
     * @param id nickname id to delete
     * @return deletion result
     */
    @DeleteMapping("/{id}")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> deleteNick(@PathVariable Long id) {
        return ResponseEntity.ofNullable(nicknameService.deleteOnQueue(id));
    }
}
