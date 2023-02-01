package com.bssmh.portfolio.web.domain.auth.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.config.security.jwt.JwtTokenService;
import com.bssmh.portfolio.web.domain.auth.constants.AuthConstants;
import com.bssmh.portfolio.web.domain.dto.JwtTokenDto;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import com.bssmh.portfolio.web.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.AUTHORIZATION;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.BEARER;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.KAKAO_AUTH_URL;
import static com.bssmh.portfolio.web.domain.auth.constants.AuthConstants.KAKAO_PROFILE_URL;
import static com.bssmh.portfolio.web.domain.enums.ClientType.KAKAO;
import static com.bssmh.portfolio.web.security.PrincipalConstants.KAKAO_ACCOUNT;
import static com.bssmh.portfolio.web.security.PrincipalConstants.PROFILE;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2KaKaoService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClinetId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String kakaoAuthorizationGrantType;

    // service
    private final JwtTokenService jwtTokenService;
    private final OAuth2LoginService oAuth2LoginService;

    public JwtTokenDto getToken(String code) {
        String kakaoAuthToken = getKakaoAuthToken(code);
        Member member = getMemberByKakaoToken(kakaoAuthToken);
        return jwtTokenService.generateJwtToken(member);
    }

    private String getKakaoAuthToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(AuthConstants.GRANT_TYPE, kakaoAuthorizationGrantType);
        params.add(AuthConstants.CLIENT_ID, kakaoClinetId);
        params.add(AuthConstants.REDIRECT_URI, kakaoRedirectUri);
        params.add(AuthConstants.CODE, code);

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        JSONObject jsonObject = getResponse(httpEntity, KAKAO_AUTH_URL);
        return (String) jsonObject.get(AuthConstants.ACCESS_TOKEN);
    }

    private Member getMemberByKakaoToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(AUTHORIZATION, BEARER + token);

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        JSONObject jsonObject = getResponse(httpEntity, KAKAO_PROFILE_URL);

        Map<String, Object> attributes = (Map<String, Object>) jsonObject.get(KAKAO_ACCOUNT);
        OAuthAttributes oAuthAttributes = OAuthAttributes.create(KAKAO.getClientId(), PROFILE, attributes);

        Member member = oAuth2LoginService.saveOrUpdate(oAuthAttributes);
        oAuth2LoginService.saveLoginLog(member);

        return member;
    }

    private JSONObject getResponse(HttpEntity<LinkedMultiValueMap<String, String>> httpEntity,
                                   String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    url,
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
