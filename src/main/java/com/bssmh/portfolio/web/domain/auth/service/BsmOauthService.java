package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import com.bssmh.portfolio.web.domain.auth.controller.rq.BsmOauthRq;
import com.bssmh.portfolio.web.domain.auth.facade.BsmAuthFacade;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.domain.enums.ClientType;
import com.bssmh.portfolio.web.exception.InvalidBsmOauthClientException;
import com.bssmh.portfolio.web.exception.NoSuchBsmAuthCodeException;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.response.BsmResourceResponse;
import leehj050211.bsmOauth.dto.response.BsmStudentResponse;
import leehj050211.bsmOauth.dto.response.BsmTeacherResponse;
import leehj050211.bsmOauth.exceptions.BsmAuthCodeNotFoundException;
import leehj050211.bsmOauth.exceptions.BsmAuthInvalidClientException;
import leehj050211.bsmOauth.exceptions.BsmAuthTokenNotFoundException;
import leehj050211.bsmOauth.type.BsmAuthUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BsmOauthService {

    // service
    private final JwtTokenService jwtTokenService;

    // facade
    private final BsmAuthFacade authFacade;

    // bsm oauth library
    private final BsmOauth bsmOauth;

    // constant
    private static final String BSM_USER_NAME_ATTRIBUTE_NAME = "code";

    public JwtTokenDto bsmLogin(BsmOauthRq rq) throws IOException {
        BsmResourceResponse resource = getResource(rq.getAuthCode());

        Map<String, Object> attributes = toAttributes(resource);
        OAuthAttributes oAuthAttributes = OAuthAttributes.create(ClientType.BSM.getClientId(), BSM_USER_NAME_ATTRIBUTE_NAME, attributes);

        Member member = authFacade.saveOrUpdate(oAuthAttributes);
        authFacade.saveLoginLog(member);

        return jwtTokenService.generateJwtToken(member);
    }

    private BsmResourceResponse getResource(String authCode) throws IOException {
        try {
            String token = bsmOauth.getToken(authCode);
            return bsmOauth.getResource(token);
        } catch (BsmAuthCodeNotFoundException | BsmAuthTokenNotFoundException e) {
            throw new NoSuchBsmAuthCodeException();
        } catch (BsmAuthInvalidClientException e) {
            throw new InvalidBsmOauthClientException();
        }
    }

    private Map<String, Object> toAttributes(BsmResourceResponse resource) {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("code", resource.getUserCode());
        attributes.put("email", resource.getEmail());

        if (resource.getRole().equals(BsmAuthUserRole.STUDENT)) {
            ofStudent(resource.getStudent(), attributes);
        }
        if (resource.getRole().equals(BsmAuthUserRole.TEACHER)) {
            ofTeacher(resource.getTeacher(), attributes);
        }

        return attributes;
    }

    private void ofStudent(BsmStudentResponse student, Map<String, Object> attributes) {
        attributes.put("name", student.getName());
    }

    private void ofTeacher(BsmTeacherResponse teacher, Map<String, Object> attributes) {
        attributes.put("name", teacher.getName());
    }

}
