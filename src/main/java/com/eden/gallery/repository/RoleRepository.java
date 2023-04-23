package com.eden.gallery.repository;

import com.eden.gallery.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for user role.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r.name from Role r")
    List<String> getName();
}
