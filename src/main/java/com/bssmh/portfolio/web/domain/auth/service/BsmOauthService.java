package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenFactory;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import com.bssmh.portfolio.web.exception.InvalidBsmOauthClientException;
import com.bssmh.portfolio.web.exception.NoSuchBsmAuthCodeException;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import com.bssmh.portfolio.web.security.PrincipalConstants;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.BsmStudent;
import leehj050211.bsmOauth.dto.resource.BsmTeacher;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import leehj050211.bsmOauth.type.BsmUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.bssmh.portfolio.web.domain.enums.ClientType.BSM;

@Service
@RequiredArgsConstructor
@Transactional
public class BsmOauthService {

    // service
    private final JwtTokenFactory jwtTokenFactory;
    private final OAuth2LoginService oAuth2LoginService;

    // bsm oauth library
    private final BsmOauth bsmOauth;

    public JwtTokenDto bsmLogin(String code) {
        try {
            BsmUserResource resource = getResource(code);

            Map<String, Object> attributes = toAttributes(resource);
            OAuthAttributes oAuthAttributes = OAuthAttributes.create(BSM.getClientId(), attributes);

            Member member = oAuth2LoginService.saveOrUpdate(oAuthAttributes);
            oAuth2LoginService.saveLoginLog(member);
            return jwtTokenFactory.generateJwtToken(member);
        } catch (IOException e) {
            throw new AuthenticationException();
        }
    }

    private BsmUserResource getResource(String authCode) throws IOException {
        try {
            String token = bsmOauth.getToken(authCode);
            return bsmOauth.getResource(token);
        } catch (BsmOAuthCodeNotFoundException | BsmOAuthTokenNotFoundException e) {
            throw new NoSuchBsmAuthCodeException();
        } catch (BsmOAuthInvalidClientException e) {
            throw new InvalidBsmOauthClientException();
        }
    }

    private Map<String, Object> toAttributes(BsmUserResource resource) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(PrincipalConstants.EMAIL, resource.getEmail());
        attributes.put(PrincipalConstants.PICTURE, resource.getProfileUrl());

        if (resource.getRole().equals(BsmUserRole.STUDENT)) {
            ofStudent(resource.getStudent(), attributes);
        }
        if (resource.getRole().equals(BsmUserRole.TEACHER)) {
            ofTeacher(resource.getTeacher(), attributes);
        }

        return attributes;
    }

    private void ofStudent(BsmStudent student, Map<String, Object> attributes) {
        attributes.put(PrincipalConstants.NAME, student.getName());
    }

    private void ofTeacher(BsmTeacher teacher, Map<String, Object> attributes) {
        attributes.put(PrincipalConstants.NAME, teacher.getName());
    }

}
