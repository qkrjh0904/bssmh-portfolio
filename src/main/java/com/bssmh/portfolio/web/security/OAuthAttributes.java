package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

import static com.bssmh.portfolio.web.domain.enums.ClientType.BSM;
import static com.bssmh.portfolio.web.domain.enums.ClientType.GOOGLE;
import static com.bssmh.portfolio.web.domain.enums.ClientType.KAKAO;
import static com.bssmh.portfolio.web.security.PrincipalConstants.EMAIL;
import static com.bssmh.portfolio.web.security.PrincipalConstants.NICKNAME;
import static com.bssmh.portfolio.web.security.PrincipalConstants.PROFILE_IMAGE_URL;

@Getter
public class OAuthAttributes implements OAuth2User {
    private Map<String, Object> attributes;
    private String registrationId;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder(access = AccessLevel.PRIVATE)
    private OAuthAttributes(String registrationId, Map<String, Object> attributes, String nameAttributeKey, String name,
                            String email, String picture) {
        this.registrationId = registrationId;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes create(String registrationId,
                                         Map<String, Object> attributes) {

        if (GOOGLE.isEqualToClientId(registrationId)) {
            return ofGoogle(attributes);
        }

        if (KAKAO.isEqualToClientId(registrationId)) {
            return ofKakao(attributes);
        }

        if (BSM.isEqualToClientId(registrationId)) {
            return ofBsm(attributes);
        }

        throw new AuthenticationException();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> profile = (Map<String, Object>) attributes.get(PrincipalConstants.PROFILE);
        return OAuthAttributes.builder()
                .registrationId(KAKAO.getClientId())
                .name((String) profile.get(NICKNAME))
                .email((String) attributes.get(EMAIL))
                .picture((String) profile.get(PROFILE_IMAGE_URL))
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .registrationId(GOOGLE.getClientId())
                .name((String) attributes.get(PrincipalConstants.NAME))
                .email((String) attributes.get(EMAIL))
                .picture((String) attributes.get(PrincipalConstants.PICTURE))
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofBsm(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .registrationId(BSM.getClientId())
                .name((String) attributes.get(PrincipalConstants.NAME))
                .email((String) attributes.get(EMAIL))
                .picture((String) attributes.get(PrincipalConstants.PICTURE))
                .attributes(attributes)
                .build();
    }

    public Member toEntity() {
        return Member.ofNormal(email, name, picture, registrationId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
