package com.eden.gallery.service.impl;

import com.eden.gallery.repository.sql.UserRepository;
import com.eden.gallery.security.JwtTokenUtils;
import com.eden.gallery.service.AuthService;
import com.eden.gallery.viewmodel.AuthRequest;
import com.eden.gallery.viewmodel.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

/**
 * Implementation for authentication service.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtils jwtTokenUtils;
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse login(AuthRequest request) {

        String password = new String(Base64.getDecoder().decode(request.getPassword()));
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        password
                ));
        User user = (User) authentication.getPrincipal();
        Optional<com.eden.gallery.model.User> userDetails = userRepository.findByUsername(user.getUsername());
        if (userDetails.isEmpty()) {
            return null;
        }
        String token = jwtTokenUtils.generateAccessToken(userDetails.get());
        return new AuthResponse(user.getUsername(), token);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
