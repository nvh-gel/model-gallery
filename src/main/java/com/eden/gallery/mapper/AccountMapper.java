package com.eden.gallery.mapper;

import com.eden.data.mapper.BaseMapper;
import com.eden.gallery.model.Account;
import com.eden.gallery.viewmodel.AccountVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static com.eden.gallery.utils.Strings.SPRING;

/**
 * Data mapper for account.
 */
@Mapper(componentModel = SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper extends BaseMapper<Account, AccountVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "active", source = "isActive")
    @Mapping(target = "verified", source = "isVerified")
    @Mapping(target = "verifyToken", ignore = true)
    Account toModel(AccountVM accountVM);

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "isActive", source = "active")
    @Mapping(target = "isVerified", source = "verified")
    AccountVM toViewModel(Account account);
}
