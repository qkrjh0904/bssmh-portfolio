package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import com.bssmh.portfolio.web.domain.member.repository.MemberLoginLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberSignUpLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
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
    private final MemberLoginLogRepository memberLoginLogRepository;
    private final MemberSignUpLogRepository memberSignUpLogRepository;
    private final MemberRepository memberRepository;

    // service
    private final FindMemberService findMemberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        Member member = this.login(attributes, clientRegistration);
        return new PrincipalDetails(member, attributes);
    }
    
    private Member login(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();
        Member member = null;
        if(ClientType.NAVER.equals(registrationId)){
            member = this.getNaverUserOrSignUp(attributes, clientRegistration);
        } else if (ClientType.GOOGLE.equals(registrationId)){
            member = this.getGoogleUserOrSignUp(attributes, clientRegistration);
        }
        
        // 로그인 로그
        this.saveLoginLog(member);
        return member;
    }
    
    private void saveLoginLog(Member member) {
        MemberLoginLog memberLoginLog = MemberLoginLog.of(member.getEmail(), member.getName(), member);
        memberLoginLogRepository.save(memberLoginLog);
    }

    private Member getGoogleUserOrSignUp(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        String email = (String) attributes.get(PrincipalConstants.EMAIL);
        String name = (String) attributes.get(PrincipalConstants.NAME);
        Member member = findMemberService.findByEmail(email);
        if(Objects.isNull(member)){
            member = this.signUpUserAndSaveLog(email, name, clientRegistration);
        }
        return member;
    }

    private Member getNaverUserOrSignUp(Map<String, Object> attributes, ClientRegistration clientRegistration) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(PrincipalConstants.RESPONSE);
        String email = (String) response.get(PrincipalConstants.EMAIL);
        String name = (String) response.get(PrincipalConstants.NAME);
        Member member = findMemberService.findByEmail(email);
        if(Objects.isNull(member)){
            member = this.signUpUserAndSaveLog(email, name, clientRegistration);
        }
        return member;
    }

    private Member signUpUserAndSaveLog(String email, String name, ClientRegistration clientRegistration) {
        String registrationId = clientRegistration.getRegistrationId();
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();

        // 유저 등록(회원가입)
        Member member = Member.ofNormal(email, name, registrationId, clientId, clientSecret);
        memberRepository.save(member);

        // 회원가입 로그
        MemberSignUpLog memberSignUpLog = MemberSignUpLog.of(email, name, member);
        memberSignUpLogRepository.save(memberSignUpLog);
        return member;
    }
}
