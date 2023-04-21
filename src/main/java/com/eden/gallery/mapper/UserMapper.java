package com.eden.gallery.mapper;

import com.eden.data.mapper.BaseMapper;
import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.User;
import com.eden.gallery.viewmodel.AuthorityVM;
import com.eden.gallery.viewmodel.UserVM;
import org.mapstruct.*;

import static com.eden.gallery.utils.Strings.SPRING;

/**
 * Data mapper for user.
 */
@Mapper(componentModel = SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserVM> {

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
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "user", source = "username", qualifiedByName = "childAuthorityUsernameToUser")
    Authorities toModel(AuthorityVM authorityVM);

    /**
     * Map child authority to view model.
     *
     * @param authorities authority data model
     * @return authority VM
     */
    @Mapping(target = "username", source = "user", qualifiedByName = "childAuthorityUserToUsername")
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
}
