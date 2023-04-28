package com.eden.gallery.mapper;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.User;
import com.eden.gallery.viewmodel.AuthorityVM;
import com.eden.gallery.viewmodel.UserVM;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-28T10:11:55+0700",
    comments = "version: 1.5.4.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230413-0857, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void mapUpdate(User arg0, User arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getCreatedAt() != null ) {
            arg0.setCreatedAt( arg1.getCreatedAt() );
        }
        arg0.setDeleted( arg1.isDeleted() );
        arg0.setId( arg1.getId() );
        if ( arg1.getUpdatedAt() != null ) {
            arg0.setUpdatedAt( arg1.getUpdatedAt() );
        }
        if ( arg1.getUuid() != null ) {
            arg0.setUuid( arg1.getUuid() );
        }
        if ( arg0.getAuthorities() != null ) {
            List<Authorities> list = arg1.getAuthorities();
            if ( list != null ) {
                arg0.getAuthorities().clear();
                arg0.getAuthorities().addAll( list );
            }
        }
        else {
            List<Authorities> list = arg1.getAuthorities();
            if ( list != null ) {
                arg0.setAuthorities( new ArrayList<Authorities>( list ) );
            }
        }
        if ( arg1.getEmail() != null ) {
            arg0.setEmail( arg1.getEmail() );
        }
        arg0.setEnabled( arg1.isEnabled() );
        if ( arg1.getPassword() != null ) {
            arg0.setPassword( arg1.getPassword() );
        }
        if ( arg1.getUsername() != null ) {
            arg0.setUsername( arg1.getUsername() );
        }
    }

    @Override
    public List<User> toModel(List<UserVM> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( arg0.size() );
        for ( UserVM userVM : arg0 ) {
            list.add( toModel( userVM ) );
        }

        return list;
    }

    @Override
    public UserVM toViewModel(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserVM userVM = new UserVM();

        userVM.setCreatedAt( arg0.getCreatedAt() );
        userVM.setId( arg0.getId() );
        userVM.setUpdatedAt( arg0.getUpdatedAt() );
        userVM.setUuid( arg0.getUuid() );
        userVM.setAuthorities( authoritiesListToAuthorityVMList( arg0.getAuthorities() ) );
        userVM.setEmail( arg0.getEmail() );
        userVM.setEnabled( arg0.isEnabled() );
        userVM.setPassword( arg0.getPassword() );
        userVM.setUsername( arg0.getUsername() );

        return userVM;
    }

    @Override
    public List<UserVM> toViewModel(List<User> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<UserVM> list = new ArrayList<UserVM>( arg0.size() );
        for ( User user : arg0 ) {
            list.add( toViewModel( user ) );
        }

        return list;
    }

    @Override
    public User toModel(UserVM userVM) {
        if ( userVM == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedAt( userVM.getCreatedAt() );
        if ( userVM.getId() != null ) {
            user.setId( userVM.getId() );
        }
        user.setUpdatedAt( userVM.getUpdatedAt() );
        user.setUuid( userVM.getUuid() );
        user.setAuthorities( authorityVMListToAuthoritiesList( userVM.getAuthorities() ) );
        user.setEmail( userVM.getEmail() );
        if ( userVM.getEnabled() != null ) {
            user.setEnabled( userVM.getEnabled() );
        }
        user.setPassword( userVM.getPassword() );
        user.setUsername( userVM.getUsername() );

        return user;
    }

    @Override
    public Authorities toModel(AuthorityVM authorityVM) {
        if ( authorityVM == null ) {
            return null;
        }

        Authorities authorities = new Authorities();

        authorities.setUser( childAuthorityUserName( authorityVM.getUsername() ) );
        authorities.setCreatedAt( authorityVM.getCreatedAt() );
        if ( authorityVM.getId() != null ) {
            authorities.setId( authorityVM.getId() );
        }
        authorities.setUpdatedAt( authorityVM.getUpdatedAt() );
        authorities.setUuid( authorityVM.getUuid() );
        authorities.setAuthority( authorityVM.getAuthority() );

        return authorities;
    }

    @Override
    public AuthorityVM toViewModel(Authorities authorities) {
        if ( authorities == null ) {
            return null;
        }

        AuthorityVM authorityVM = new AuthorityVM();

        authorityVM.setUsername( childAuthorityUserToUsername( authorities.getUser() ) );
        authorityVM.setCreatedAt( authorities.getCreatedAt() );
        authorityVM.setId( authorities.getId() );
        authorityVM.setUpdatedAt( authorities.getUpdatedAt() );
        authorityVM.setUuid( authorities.getUuid() );
        authorityVM.setAuthority( authorities.getAuthority() );

        return authorityVM;
    }

    protected List<AuthorityVM> authoritiesListToAuthorityVMList(List<Authorities> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorityVM> list1 = new ArrayList<AuthorityVM>( list.size() );
        for ( Authorities authorities : list ) {
            list1.add( toViewModel( authorities ) );
        }

        return list1;
    }

    protected List<Authorities> authorityVMListToAuthoritiesList(List<AuthorityVM> list) {
        if ( list == null ) {
            return null;
        }

        List<Authorities> list1 = new ArrayList<Authorities>( list.size() );
        for ( AuthorityVM authorityVM : list ) {
            list1.add( toModel( authorityVM ) );
        }

        return list1;
    }
}
