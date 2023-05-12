package com.eden.gallery.controller;

import com.eden.gallery.aop.LogExecutionTime;
import com.eden.gallery.aop.ResponseHandling;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eden.gallery.utils.UserRole.ROLE_USER;

/**
 * Rest controller for user managing.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    /**
     * Create a user from request
     *
     * @param request creation request.
     * @return response of creation result
     */
    @PostMapping("/register")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> createUser(@RequestBody UserVM request) {
        return ResponseEntity.ofNullable(userService.createOnQueue(request, ROLE_USER));
    }

    /**
     * Get current user information.
     *
     * @return user info
     */
    @GetMapping("/me")
    @LogExecutionTime
    @ResponseHandling
    public ResponseEntity<Object> getCurrentUserInfo() {
        return ResponseEntity.ofNullable(userService.getCurrentUser());
    }
}
