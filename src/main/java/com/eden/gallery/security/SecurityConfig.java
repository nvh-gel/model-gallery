package com.eden.gallery.security;

import com.eden.gallery.repository.mongo.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Security configuration for application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private ConfigRepository configRepository;

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
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
                .permitAll();

        http.authorizeHttpRequests()
                .requestMatchers("/user/register", "/auth/login").anonymous()
                .requestMatchers("/", "/healthz").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * CORS configuration for front end API.
     *
     * @return cors config source
     */
    @Bean
    public CorsConfigurationSource configSource() {

        List<String> allowOrigins = configRepository.findById("ALLOWED_ORIGINS")
                .map(config -> Arrays.stream(config.getValue().split(",")).toList())
                .orElseGet(Collections::emptyList);
        List<String> allowMethods = configRepository.findById("ALLOWED_METHODS")
                .map(config -> Arrays.stream(config.getValue().split(",")).toList())
                .orElseGet(Collections::emptyList);
        List<String> allowHeaders = configRepository.findById("ALLOWED_HEADERS")
                .map(config -> Arrays.stream(config.getValue().split(",")).toList())
                .orElseGet(Collections::emptyList);

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowedMethods(allowMethods);
        configuration.setAllowedHeaders(allowHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configure CORS Filter bean with custom cors configuration source.
     *
     * @return cors filter bean
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(configSource());
    }

    /**
     * Setter.
     */
    @Autowired
    public void setJwtTokenFilter(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setConfigRepository(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
}
