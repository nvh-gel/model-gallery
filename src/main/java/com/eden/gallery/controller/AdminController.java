package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for admin task handling.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

    /**
     * Add a user with specific role to system.
     *
     * @param request user data
     * @param roles   array of roles
     * @return creation result
     */
    @PostMapping("/user")
    public ResponseModel addUserWithRole(@RequestBody UserVM request, @RequestParam("roles") String[] roles) {

        return ResponseModel.created(userService.createOnQueue(request, roles));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
