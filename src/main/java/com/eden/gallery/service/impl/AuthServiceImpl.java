package com.eden.gallery.service.impl;

import com.eden.gallery.security.jwt.JwtTokenUtils;
import com.eden.gallery.service.AuthService;
import com.eden.gallery.utils.RoleUtils;
import com.eden.gallery.viewmodel.AuthRequest;
import com.eden.gallery.viewmodel.AuthResponse;
import com.eden.gallery.viewmodel.AuthorityVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * Implementation for authentication service.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final RoleUtils roleUtils;

    /**
     * Constructor.
     *
     * @param authenticationConfiguration authentication configuration bean
     * @param jwtTokenUtils               JWT processing bean
     * @param roleUtils                   user role utils bean
     * @throws Exception if authentication manager not exist
     */
    @Autowired
    public AuthServiceImpl(AuthenticationConfiguration authenticationConfiguration,
                           JwtTokenUtils jwtTokenUtils,
                           RoleUtils roleUtils) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtTokenUtils = jwtTokenUtils;
        this.roleUtils = roleUtils;
    }

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
        String token = jwtTokenUtils.generateAccessToken(user);
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        List<AuthorityVM> userAuthorities = roleUtils.getRoles().stream()
                .filter(role -> authorities.contains(role.getName()))
                .map(role -> new AuthorityVM(
                        user.getUsername(),
                        role.getName(),
                        role.getDefaultUrl(),
                        role.getLevel(),
                        role.getPages()
                )).toList();
        Integer level = userAuthorities.stream().findFirst().map(AuthorityVM::getLevel).orElse(0);
        String defaultUrl = userAuthorities.stream().findFirst().map(AuthorityVM::getDefaultUrl).orElse("/");
        return new AuthResponse(user.getUsername(), token, level, defaultUrl, userAuthorities);
    }
}
