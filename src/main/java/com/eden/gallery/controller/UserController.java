package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for user managing.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    /**
     * Create a user from request
     *
     * @param request creation request.
     * @return response of creation result
     */
    @PostMapping
    public ResponseModel createUser(@RequestBody UserVM request) {

        return ResponseModel.created(userService.createOnQueue(request, "USER"));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
