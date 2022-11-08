package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.user.User;
import com.bssmh.portfolio.db.entity.user.UserLoginLog;
import com.bssmh.portfolio.db.entity.user.UserRegisterLog;
import com.bssmh.portfolio.db.enums.UserRoleType;
import com.bssmh.portfolio.web.user.service.FindUserService;
import com.bssmh.portfolio.web.user.repository.UserLoginLogRepository;
import com.bssmh.portfolio.web.user.repository.UserRegisterLogRepository;
import com.bssmh.portfolio.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // repository
    private final UserLoginLogRepository userLoginLogRepository;
    private final UserRegisterLogRepository userRegisterLogRepository;
    private final UserRepository userRepository;

    // service
    private final FindUserService findUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        User user = this.getUser(attributes, clientRegistration);
        return new PrincipalDetails(user, attributes);
    }

    /**
     * 로그인
     */
    private User getUser(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String email = (String) attributes.get(PrincipalConstants.EMAIL);
        String name = (String) attributes.get(PrincipalConstants.NAME);
        String locale = (String) attributes.get(PrincipalConstants.LOCALE);

        User user = findUserService.findByEmail(email);
        if (Objects.isNull(user)) {
            user = this.registerUserAndLog(attributes, clientRegistration);
        }

        UserLoginLog userLoginLog = UserLoginLog.of(email, name, locale, user);
        userLoginLogRepository.save(userLoginLog);
        return user;
    }

    /**
     * 회원가입
     */
    private User registerUserAndLog(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String email = (String) attributes.get(PrincipalConstants.EMAIL);
        String sub = (String) attributes.get(PrincipalConstants.SUB);
        String name = (String) attributes.get(PrincipalConstants.NAME);
        String givenName = (String) attributes.get(PrincipalConstants.GIVEN_NAME);
        String familyName = (String) attributes.get(PrincipalConstants.FAMILY_NAME);
        Boolean emailVerified = (Boolean) attributes.get(PrincipalConstants.EMAIL_VERIFIED);
        String locale = (String) attributes.get(PrincipalConstants.LOCALE);

        String registrationId = clientRegistration.getRegistrationId();
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        User user = User.of(email, name, sub, givenName, familyName, emailVerified, locale, registrationId, clientId,
                clientSecret, UserRoleType.NORMAL);
        userRepository.save(user);

        UserRegisterLog userRegisterLog = UserRegisterLog.of(email, name, locale, user);
        userRegisterLogRepository.save(userRegisterLog);
        return user;
    }
}
