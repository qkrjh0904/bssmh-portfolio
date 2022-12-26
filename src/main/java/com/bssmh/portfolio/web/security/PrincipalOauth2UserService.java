package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberLoginLog;
import com.bssmh.portfolio.db.entity.member.MemberSignUpLog;
import com.bssmh.portfolio.web.domain.member.repository.MemberLoginLogRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberRepository;
import com.bssmh.portfolio.web.domain.member.repository.MemberSignUpLogRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
        OAuth2User oAuth2User = super.loadUser(userRequest);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        String registrationId = clientRegistration.getRegistrationId();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = this.saveOrUpdate(oAuthAttributes);
        this.saveLoginLog(member);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getMemberRoleType().getRoleName())),
                oAuthAttributes.getAttributes(),
                oAuthAttributes.getNameAttributeKey());
    }
    
    private Member saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Member member = findMemberService.findByEmail(oAuthAttributes.getEmail());
        if(Objects.isNull(member)){
            member = oAuthAttributes.toEntity();
            memberRepository.save(member);
            this.saveSignUpLog(member);
        }

        member.update(oAuthAttributes.getName(), oAuthAttributes.getPicture());
        return member;
    }

    private void saveLoginLog(Member member) {
        MemberLoginLog memberLoginLog = MemberLoginLog.of(member.getEmail(), member.getName(), member);
        memberLoginLogRepository.save(memberLoginLog);
    }

    private void saveSignUpLog(Member member) {
        MemberSignUpLog memberSignUpLog = MemberSignUpLog.of(member.getEmail(), member.getName(), member);
        memberSignUpLogRepository.save(memberSignUpLog);
    }

}
