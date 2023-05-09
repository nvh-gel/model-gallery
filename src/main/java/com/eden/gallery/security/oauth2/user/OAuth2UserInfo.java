package com.eden.gallery.security.oauth2.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Abstract user info data from OAuth Providers.
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    /**
     * Get user id from oauth attributes.
     *
     * @return user id
     */
    public abstract String getId();

    /**
     * Get name from oauth attributes.
     *
     * @return user's name
     */
    public abstract String getName();

    /**
     * Get email from oauth attributes.
     *
     * @return user's email
     */
    public abstract String getEmail();

    /**
     * Get user thumbnail image from oauth attributes.
     *
     * @return image url
     */
    public abstract String getImageUrl();
}
