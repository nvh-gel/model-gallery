package com.eden.gallery.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Enum for user roles.
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRole {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_MODERATOR = "MODERATOR";
    public static final String ROLE_ADMIN = "ADMIN";
}
