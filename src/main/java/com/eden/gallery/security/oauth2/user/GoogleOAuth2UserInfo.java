package com.eden.gallery.security.oauth2.user;

import java.util.Map;

/**
 * OAuth User info retrieved from Google.
 */
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    /**
     * New Google user info from attributes.
     *
     * @param attributes retrieved attributes
     */
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return String.valueOf(attributes.get("sub"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getImageUrl() {
        return String.valueOf(attributes.get("picture"));
    }
}
