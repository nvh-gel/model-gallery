package com.eden.gallery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * Configuration for user verification.
 */
@Configuration
public class UserDetailsConfiguration {

    private DataSource dataSource;

    /**
     * Configuration for user service.
     *
     * @return user service for authentication and authorization
     */
    @Bean
    public UserDetailsManager userDetailsManager() {

        return new JdbcUserDetailsManager(dataSource);
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

    /**
     * Setter.
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
