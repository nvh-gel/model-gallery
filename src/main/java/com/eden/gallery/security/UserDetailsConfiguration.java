package com.eden.gallery.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuration for user verification.
 */
@Configuration
public class UserDetailsConfiguration {

    /**
     * Configuration for user service.
     *
     * @param bCryptPasswordEncoder password encrypt bean
     * @return user service for authentication and authorization
     */
    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .roles("USER")
                .password(bCryptPasswordEncoder.encode("user"))
                .build());
        manager.createUser(User.withUsername("admin")
                .roles("ADMIN")
                .password(bCryptPasswordEncoder.encode("admin"))
                .build());
        return manager;
    }

    /**
     * Password encoder bean.
     *
     * @return Bcrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
