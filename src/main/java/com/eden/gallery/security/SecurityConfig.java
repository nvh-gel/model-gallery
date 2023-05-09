package com.eden.gallery.security;

import com.eden.gallery.security.jwt.JwtTokenFilter;
import com.eden.gallery.security.oauth2.CustomOAuth2UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

    @Value("${spring.security.allowed.origins}")
    private List<String> allowOrigins;

    private JwtTokenFilter jwtTokenFilter;
    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
    private CustomOAuth2UserService customOAuth2UserService;
    private AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

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
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/error")
                .permitAll()
                .requestMatchers("/user/register", "/auth/**", "/oauth2/**")
                .permitAll()
                .requestMatchers("/", "/healthz")
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .authorizationEndpoint().authorizationRequestRepository(authorizationRequestRepository)
                .and().redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and().userInfoEndpoint().userService(customOAuth2UserService)
                .and().successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

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
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
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
