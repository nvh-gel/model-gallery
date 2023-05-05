package com.eden.gallery.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;

/**
 * Security configuration for application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private JwtTokenFilter jwtTokenFilter;
    @Value("${spring.security.allowed.methods}")
    private List<String> allowMethods;
    @Value("${spring.security.allowed.headers}")
    private List<String> allowHeaders;
    @Value("${spring.security.allowed.origins}")
    private List<String> allowOrigins;

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
}
