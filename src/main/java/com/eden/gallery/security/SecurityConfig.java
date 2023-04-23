package com.eden.gallery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private JwtTokenFilter jwtTokenFilter;

    /**
     * Define filter chain.
     *
     * @param http security context
     * @return filter chain
     * @throws Exception if filter chain was already created
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/user", "/auth/login").anonymous()
                .requestMatchers("/", "/healthz").anonymous()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setJwtTokenFilter(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }
}
