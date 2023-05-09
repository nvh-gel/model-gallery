package com.eden.gallery.security.oauth2.user;

import com.eden.gallery.model.Authorities;
import com.eden.gallery.model.Role;
import com.eden.gallery.model.User;
import com.eden.gallery.repository.sql.AuthoritiesRepository;
import com.eden.gallery.repository.sql.UserRepository;
import com.eden.gallery.security.oauth2.AuthProvider;
import com.eden.gallery.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * User management for OAuth2.
 */
@Service
@AllArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;
    private AuthoritiesRepository authoritiesRepository;

    /**
     * Load user detail from oauth2 request and process it.
     *
     * @param userRequest the user request
     * @return created or saved user
     * @throws OAuth2AuthenticationException if process user failed
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading user.");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return this.processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Create new user or update existing one.
     *
     * @param request    oauth2 user request
     * @param oAuth2User loaded user
     * @return user principal
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.produce(
                request.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );
        if (!StringUtils.hasText(userInfo.getEmail())) {
            throw new AuthenticationCredentialsNotFoundException("Email not found by provider.");
        }
        Optional<User> existing = userRepository.findByEmail(userInfo.getEmail());
        User user;
        if (existing.isPresent()) {
            user = existing.get();
            if (!user.getProvider().equals(
                    AuthProvider.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase()))
            ) {
                throw new OAuth2AuthenticationException("Invalid sign up with " + user.getProvider() + " account");
            }
            user = updateUser(user, userInfo);
        } else {
            user = registerUser(request, userInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    /**
     * Create new user in database.
     *
     * @param request  oauth2 user request
     * @param userInfo user info from oauth2 provider
     * @return created user
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    private User registerUser(OAuth2UserRequest request, OAuth2UserInfo userInfo) {
        log.info("Register new oauth user.");
        User user = new User();
        user.setUsername(userInfo.getEmail());
        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setImageUrl(userInfo.getImageUrl());
        user.setProvider(AuthProvider.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setProviderId(userInfo.getId());
        user.setUuid(UUID.randomUUID());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User created = userRepository.save(user);

        Authorities authorities = new Authorities(Role.user(), Constants.ROLE_USER, created);
        authorities.setUuid(UUID.randomUUID());
        authorities.setCreatedAt(LocalDateTime.now());
        authorities.setUpdatedAt(LocalDateTime.now());
        authoritiesRepository.save(authorities);

        return created;
    }

    /**
     * Update existing user.
     *
     * @param user     user to update
     * @param userInfo user info from oauth2 provider
     * @return updated user
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        log.info("Update existing oauth user.");
        user.setName(userInfo.getName());
        user.setImageUrl(userInfo.getImageUrl());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
