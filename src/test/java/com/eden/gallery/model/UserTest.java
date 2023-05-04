package com.eden.gallery.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void isExpired() {
        User user = new User();
        Assertions.assertTrue(user.isAccountNonExpired());
        user.setExpired(true);
        Assertions.assertFalse(user.isAccountNonExpired());
    }

    @Test
    void isLocked() {
        User user = new User();
        Assertions.assertTrue(user.isAccountNonLocked());
        user.setLocked(true);
        Assertions.assertFalse(user.isAccountNonLocked());
    }

    @Test
    void isCredentialExpired() {
        User user = new User();
        Assertions.assertTrue(user.isCredentialsNonExpired());
        user.setCredentialExpired(true);
        Assertions.assertFalse(user.isCredentialsNonExpired());
    }
}
