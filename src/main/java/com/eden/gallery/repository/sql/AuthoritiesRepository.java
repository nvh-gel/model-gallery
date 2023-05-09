package com.eden.gallery.repository.sql;

import com.eden.gallery.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User Authority.
 */
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
