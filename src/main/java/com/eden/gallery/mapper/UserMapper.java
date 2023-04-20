package com.eden.gallery.mapper;

import com.eden.data.mapper.BaseMapper;
import com.eden.gallery.model.Authority;
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

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "user", source = "username", qualifiedByName = "childAuthorityUsernameToUser")
    Authority toModel(AuthorityVM authorityVM);

    @Mapping(target = "username", source = "user", qualifiedByName = "childAuthorityUserToUsername")
    AuthorityVM toViewModel(Authority authority);

    @Named("childAuthorityUsernameToUser")
    default User childAuthorityUserName(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }

    @Named("childAuthorityUserToUsername")
    default String childAuthorityUserToUsername(User user) {
        return user.getUsername();
    }
}
