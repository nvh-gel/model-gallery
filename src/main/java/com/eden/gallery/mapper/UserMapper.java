package com.eden.gallery.mapper;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.Role;
import com.eden.gallery.model.User;
import com.eden.gallery.viewmodel.AuthorityVM;
import com.eden.gallery.viewmodel.UserVM;
import com.eden.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Data mapper for user.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<UserVM, User> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    User toModel(UserVM userVM);

    /**
     * Map child authority VMs to data model
     *
     * @param authorityVM vm to convert
     * @return authority data
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", source = "username", qualifiedByName = "childAuthorityUsernameToUser")
    @Mapping(target = "authority", source = "authority", qualifiedByName = "authorityStringToRole")
    Authorities toModel(AuthorityVM authorityVM);

    /**
     * Map child authority to view model.
     *
     * @param authorities authority data model
     * @return authority VM
     */
    @Mapping(target = "username", source = "user", qualifiedByName = "childAuthorityUserToUsername")
    @Mapping(target = "level", source = "level")
    @Mapping(target = "pages", source = "pages")
    AuthorityVM toViewModel(Authorities authorities);

    /**
     * Map username to child user for relation.
     *
     * @param username username to map
     * @return user data for relation
     */
    @Named("childAuthorityUsernameToUser")
    default User childAuthorityUserName(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }

    /**
     * Map child user to username for VM.
     *
     * @param user child user to map
     * @return username
     */
    @Named("childAuthorityUserToUsername")
    default String childAuthorityUserToUsername(User user) {
        return user.getUsername();
    }

    @Named("authorityStringToRole")
    default Role authorityStringToRole(String authority) {
        Role role = new Role();
        role.setName(authority);
        return role;
    }
}
