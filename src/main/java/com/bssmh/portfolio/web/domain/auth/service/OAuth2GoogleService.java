package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenFactory;
import com.bssmh.portfolio.web.domain.auth.constants.AuthConstants;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.AUTHORIZATION;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.AUTHORIZATION_CODE;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.BEARER;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.GOOGLE_AUTH_URL;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.GOOGLE_PROFILE_URL;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.KAKAO_PROFILE_URL;
import static com.bssmh.portfolio.web.domain.enums.ClientType.GOOGLE;
import static com.bssmh.portfolio.web.domain.enums.ClientType.KAKAO;
import static com.bssmh.portfolio.web.security.PrincipalConstants.KAKAO_ACCOUNT;
import static com.bssmh.portfolio.web.security.PrincipalConstants.PROFILE;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2GoogleService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;


    // service
    private final JwtTokenFactory jwtTokenFactory;
    private final OAuth2LoginService oAuth2LoginService;

    public JwtTokenDto getToken(String code) {
        String googleAuthToken = getGoogleAuthToken(code);
        Member member = getMemberByGoogleToken(googleAuthToken);
        return jwtTokenFactory.generateJwtToken(member);
    }

    private String getGoogleAuthToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AuthConstants.GRANT_TYPE, AUTHORIZATION_CODE);
        params.add(AuthConstants.CLIENT_ID, googleClientId);
        params.add(AuthConstants.CLIENT_SECRET, googleClientSecret);
        params.add(AuthConstants.REDIRECT_URI, googleRedirectUri);
        params.add(AuthConstants.CODE, code);

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        JSONObject jsonObject = getResponse(httpEntity, GOOGLE_AUTH_URL, HttpMethod.POST);
        return (String) jsonObject.get(AuthConstants.ACCESS_TOKEN);
    }

    private Member getMemberByGoogleToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(AUTHORIZATION, BEARER + token);

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        Map<String, Object> attributes = (Map<String, Object>) getResponse(httpEntity, GOOGLE_PROFILE_URL, HttpMethod.GET);
        OAuthAttributes oAuthAttributes = OAuthAttributes.create(GOOGLE.getClientId(), attributes);

        Member member = oAuth2LoginService.saveOrUpdate(oAuthAttributes);
        oAuth2LoginService.saveLoginLog(member);

        return member;
    }

    private JSONObject getResponse(HttpEntity<LinkedMultiValueMap<String, String>> httpEntity,
                                   String url,
                                   HttpMethod httpMethod) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    httpMethod,
                    httpEntity,
                    String.class);

            String body = response.getBody();
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(body);

        } catch (ParseException e) {
            throw new AuthenticationException();
        }
    }

}
