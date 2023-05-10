package com.eden.gallery.utils;

import com.eden.gallery.model.Role;
import com.eden.gallery.repository.sql.RoleRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utilization for user roles.
 */
@Component
@Getter
public class RoleUtils {

    private final List<Role> roles;

    /**
     * Constructor.
     *
     * @param roleRepository repository bean for role
     */
    public RoleUtils(RoleRepository roleRepository) {
        roles = roleRepository.findAllByOrderByLevelDesc();
    }

    /**
     * Retrieve role config by name.
     *
     * @param role role name
     * @return role config
     */
    public Role getByRoleName(String role) {
        return roles.stream().filter(r -> role.equals(r.getName())).findFirst().orElse(null);
    }
}
