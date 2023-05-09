package com.eden.gallery.security.oauth2;

import com.eden.gallery.security.jwt.JwtTokenUtils;
import com.eden.gallery.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import static com.eden.gallery.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@AllArgsConstructor
@Log4j2
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String[] authorizedRedirectUris = new String[]{"http://localhost:3000/oauth2/redirect"};

    private JwtTokenUtils jwtTokenUtils;
    private HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain,
                                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.info("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadCredentialsException("Unauthorized redirect URI.");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = jwtTokenUtils.generateAccessToken((UserDetails) authentication.getPrincipal());
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return Arrays.stream(authorizedRedirectUris).anyMatch(u -> {
            URI authorizedURI = URI.create(u);
            return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && authorizedURI.getPort() == clientRedirectUri.getPort();
        });
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequest(request, response);
    }
}
