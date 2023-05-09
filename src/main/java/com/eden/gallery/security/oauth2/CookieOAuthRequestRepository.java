package com.eden.gallery.security.oauth2;

import com.eden.gallery.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Cookie repository to save request oauth2 request and redirect uri.
 */
@Component
@Log4j2
public class CookieOAuthRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OATH2_AUTH_REQUEST_COOKIE_NAME = "oath2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int EXPIRED_SECONDS = 600;

    /**
     * Read cookie for auth request.
     *
     * @param request the {@code HttpServletRequest}
     * @return oauth2 request
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OATH2_AUTH_REQUEST_COOKIE_NAME)
                .map(c -> CookieUtils.deserialize(c, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    /**
     * Save oauth request and redirect url into cookie.
     *
     * @param authorizationRequest the {@link OAuth2AuthorizationRequest}
     * @param request              the {@code HttpServletRequest}
     * @param response             the {@code HttpServletResponse}
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        if (null == authorizationRequest) {
            CookieUtils.deleteCookie(request, response, OATH2_AUTH_REQUEST_COOKIE_NAME);
            CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookieUtils.addCookie(response,
                OATH2_AUTH_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest),
                EXPIRED_SECONDS);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.hasText(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, EXPIRED_SECONDS);
        }
    }

    /**
     * Reload oauth request from cookie.
     *
     * @param request  the {@code HttpServletRequest}
     * @param response the {@code HttpServletResponse}
     * @return saved oauth request
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }
}
