package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

import static com.bssmh.portfolio.web.security.ClientType.GOOGLE;
import static com.bssmh.portfolio.web.security.ClientType.NAVER;

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

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if (NAVER.getClientId().equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }

        if (GOOGLE.getClientId().equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }

        // TODO: 2022-12-27 BSM Oauth2.0 추가 예정
        return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);
        return OAuthAttributes.builder()
                .registrationId(NAVER.getClientId())
                .name((String) response.get(PrincipalConstants.NAME))
                .email((String) response.get(PrincipalConstants.EMAIL))
                .picture((String) response.get(PrincipalConstants.PROFILE_IMAGE))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .registrationId(GOOGLE.getClientId())
                .name((String) attributes.get(PrincipalConstants.NAME))
                .email((String) attributes.get(PrincipalConstants.EMAIL))
                .picture((String) attributes.get(PrincipalConstants.PICTURE))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
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
