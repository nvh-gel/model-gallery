package com.eden.gallery.security.oauth2.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
