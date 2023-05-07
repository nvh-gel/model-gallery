package com.eden.gallery.security.jwt;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.Role;
import com.eden.gallery.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Component
@Log4j2
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenUtils jwtTokenUtils;

    /**
     * Filter by JWT token
     *
     * @param request     http request
     * @param response    http response from previous chain
     * @param filterChain filter chain
     * @throws ServletException if authentication fail
     * @throws IOException      if serialization fail
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getAccessToken(request);
        if (!jwtTokenUtils.validateAccessToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    /**
     * Set security data to authentication context.
     *
     * @param token   jwt token
     * @param request http request
     */
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * Check if request has authorization header.
     *
     * @param request http request
     * @return true if request has authorization, else false
     */
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return StringUtils.hasText(header) && header.startsWith("Bearer ");
    }

    /**
     * Retrieve jwt token from request authorization header.
     *
     * @param request http request
     * @return token
     */
    private String getAccessToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }

    /**
     * Parse JWT token to user details.
     *
     * @param token request access token
     * @return user data
     */
    private UserDetails getUserDetails(String token) {
        String[] jwtSubjects = jwtTokenUtils.getSubject(token).split(" ");
        User user = new User();
        user.setUsername(jwtSubjects[0]);
        List<Authorities> authorities = Arrays.stream(jwtSubjects[2].split(","))
                .map(str -> new Authorities(new Role(str, null, 0, null), user))
                .toList();
        user.setAuthorities(authorities);
        return user;
    }
}
