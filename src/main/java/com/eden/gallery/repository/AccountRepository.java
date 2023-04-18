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

    Optional<Account> findAccountByIdAndVerifyTokenEquals(Long id, String verifyToken);
}
