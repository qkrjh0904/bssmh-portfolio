package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.user.User;
import com.bssmh.portfolio.db.entity.user.UserLoginLog;
import com.bssmh.portfolio.db.entity.user.UserSignUpLog;
import com.bssmh.portfolio.web.user.repository.UserLoginLogRepository;
import com.bssmh.portfolio.web.user.repository.UserSignUpLogRepository;
import com.bssmh.portfolio.web.user.repository.UserRepository;
import com.bssmh.portfolio.web.user.service.FindUserService;
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
    private final UserSignUpLogRepository userSignUpLogRepository;
    private final UserRepository userRepository;

    // service
    private final FindUserService findUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        User user = this.login(attributes, clientRegistration);
        return new PrincipalDetails(user, attributes);
    }
    
    private User login(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();
        User user = null;
        if(ClientType.NAVER.equals(registrationId)){
            user = this.getNaverUserOrSignUp(attributes, clientRegistration);
        } else if (ClientType.GOOGLE.equals(registrationId)){
            user = this.getGoogleUserOrSignUp(attributes, clientRegistration);
        }
        
        // 로그인 로그
        this.saveLoginLog(user);
        return user;
    }
    
    private void saveLoginLog(User user) {
        UserLoginLog userLoginLog = UserLoginLog.of(user.getEmail(), user.getName(), user);
        userLoginLogRepository.save(userLoginLog);
    }

    private User getGoogleUserOrSignUp(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String email = (String) attributes.get(PrincipalConstants.EMAIL);
        String name = (String) attributes.get(PrincipalConstants.NAME);
        User user = findUserService.findByEmail(email);
        if(Objects.isNull(user)){
            user = this.signUpUserAndSaveLog(email, name, clientRegistration);
        }
        return user;
    }

    private User getNaverUserOrSignUp(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(PrincipalConstants.RESPONSE);
        String email = (String) response.get(PrincipalConstants.EMAIL);
        String name = (String) response.get(PrincipalConstants.NAME);
        User user = findUserService.findByEmail(email);
        if(Objects.isNull(user)){
            user = this.signUpUserAndSaveLog(email, name, clientRegistration);
        }
        return user;
    }

    private User signUpUserAndSaveLog(String email, String name, ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        // 유저 등록(회원가입)
        User user = User.ofNormal(email, name, registrationId, clientId, clientSecret);
        userRepository.save(user);

        // 회원가입 로그
        UserSignUpLog userSignUpLog = UserSignUpLog.of(email, name, user);
        userSignUpLogRepository.save(userSignUpLog);
        return user;
    }
}
