package com.eden.gallery.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Utilization for handling JWT token.
 */
@Component
@Log4j2
public class JwtTokenUtils {

    @Value("${spring.security.jwt.token.expire:86400000}")
    private long tokenExpireMs;

    @Value("${spring.security.jwt.token.secret}")
    private String secretKey;

    /**
     * Generate JWT token from user data.
     *
     * @param user user data
     * @return token string
     */
    public String generateAccessToken(User user) {
        String subject = "%s %s %s".formatted(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
        );
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("CodeJava")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireMs))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate the request token.
     *
     * @param token jwt token
     * @return true if valid, else false
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (IllegalArgumentException ex) {
            log.error("Token is null or empty: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Token is expired: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Token is invalid: {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("Signature validation failed: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * Retrieve subject from access token.
     *
     * @param token jwt token
     * @return subject string
     */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Parse access token to claims.
     *
     * @param token jwt token
     * @return claims data
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
