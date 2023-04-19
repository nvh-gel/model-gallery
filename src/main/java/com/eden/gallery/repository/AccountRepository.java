package com.eden.gallery.repository;

import com.eden.gallery.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for account ORM.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find a single account by id and verification token
     *
     * @param id          account id
     * @param verifyToken account verification token
     * @return optional of found account
     */
    Optional<Account> findAccountByIdAndVerifyTokenEquals(Long id, String verifyToken);

    /**
     * Find a single account username and password.
     *
     * @param username username to find
     * @param password password to find
     * @return optional of found account
     */
    Optional<Account> findAccountByUsernameAndPasswordEquals(String username, String password);
}
