package com.eden.gallery.security.oauth2.user;

import com.eden.gallery.security.oauth2.AuthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.ProviderNotFoundException;

import java.util.Map;

/**
 * Factory to create user info.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2UserInfoFactory {

    /**
     * Produce a user info base on input provider and attributes.
     *
     * @param registrationId provider name { google }
     * @param attributes     oauth attributes
     * @return created user info
     */
    public static OAuth2UserInfo produce(String registrationId, Map<String, Object> attributes) {
        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new ProviderNotFoundException("Provider " + registrationId + " is not support yet.");
        }
    }
}
