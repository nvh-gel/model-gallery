package com.eden.gallery.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Enum for user roles.
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_MODERATOR = "MODERATOR";
    public static final String ROLE_ADMIN = "ADMIN";
}
