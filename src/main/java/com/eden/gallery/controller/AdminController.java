package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
public class AdminController {

    private UserService userService;

    /**
     * Add a user with specific role to system.
     *
     * @param request user data
     * @param roles   array of roles
     * @return creation result
     */
    @PostMapping("/user")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> addUserWithRole(@RequestBody UserVM request, @RequestParam("roles") String[] roles) {
        return ResponseEntity.ofNullable(userService.createOnQueue(request, roles));
    }

    /**
     * Get a list of all users.
     *
     * @param page page number
     * @param size page size
     * @return list of users
     */
    @GetMapping("/user/{page}/{size}")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getAllUsers(@PathVariable Integer page, @PathVariable Integer size) {
        return ResponseEntity.ofNullable(userService.findAll(page, size));
    }
}
