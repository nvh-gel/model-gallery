package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eden.gallery.security.Role.ROLE_USER;

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
    @PostMapping("/register")
    public ResponseModel createUser(@RequestBody UserVM request) {

        return ResponseModel.created(userService.createOnQueue(request, ROLE_USER));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
