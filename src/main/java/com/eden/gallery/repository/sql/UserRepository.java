package com.eden.gallery.repository.sql;

import com.eden.gallery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a single user by username.
     *
     * @param username username to find
     * @return optional of found user
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a single user by email.
     *
     * @param email email to find.
     * @return optional of found user
     */
    Optional<User> findByEmail(String email);
}
