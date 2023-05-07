package com.eden.gallery.security.oauth2;

import com.eden.gallery.model.User;
import com.eden.gallery.repository.sql.UserRepository;
import com.eden.gallery.security.oauth2.user.OAuth2UserInfo;
import com.eden.gallery.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return this.processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.produce(
                request.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );
        if (StringUtils.hasText(userInfo.getEmail())) {
            throw new AuthenticationCredentialsNotFoundException("Email not found");
        }
        Optional<User> existing = userRepository.findByEmail(userInfo.getEmail());
        User user = null;
        if (existing.isPresent()) {
            user = existing.get();
        } else {
        }

        return null;
    }
}
