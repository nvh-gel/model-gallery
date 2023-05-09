package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.UserService;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.UserVM;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.eden.gallery.utils.UserRole.ROLE_ADMIN;

/**
 * Rest controller for admin task handling.
 */
@RestController
@RequestMapping("/admin")
@RolesAllowed({ROLE_ADMIN})
@AllArgsConstructor
public class AdminController {

    private final PageConverter<UserVM> pageConverter = new PageConverter<>();

    private UserService userService;

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
}
