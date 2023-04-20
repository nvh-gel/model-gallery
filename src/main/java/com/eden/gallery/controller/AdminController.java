package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    UserService userService;

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
