package com.eden.gallery.repository.sql;

import com.eden.gallery.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for user role.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Find a list of roles order by access level.
     *
     * @return list of user roles
     */
    List<Role> findAllByOrderByLevelDesc();
}
