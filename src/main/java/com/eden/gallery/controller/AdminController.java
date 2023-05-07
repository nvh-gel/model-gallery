package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.UserVM;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.eden.gallery.utils.UserRole.ROLE_ADMIN;

/**
 * Rest controller for admin task handling.
 */
@RestController
@RequestMapping("/admin")
@RolesAllowed({ROLE_ADMIN})
public class AdminController {

    UserService userService;
    PageConverter<UserVM> pageConverter = new PageConverter<>();

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
     * Get a list of all users.
     *
     * @param page page number
     * @param size page size
     * @return list of users
     */
    @GetMapping("/user/{page}/{size}")
    public ResponseModel getAllUsers(@PathVariable Integer page, @PathVariable Integer size) {

        return pageConverter.toResponseFromIterable(userService.findAll(page, size));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
