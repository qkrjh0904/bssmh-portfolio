package com.bssmh.portfolio.web.security;

import com.bssmh.portfolio.db.entity.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
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

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if(ClientType.NAVER.equals(registrationId)){
            return ofNaver(userNameAttributeName, attributes);
        }else if (ClientType.GOOGLE.equals(registrationId)){
            return ofGoogle(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);
        return OAuthAttributes.builder()
                .registrationId(ClientType.NAVER.getClientId())
                .name((String) response.get(PrincipalConstants.NAME))
                .email((String) response.get(PrincipalConstants.EMAIL))
                .picture((String) response.get(PrincipalConstants.PROFILE_IMAGE))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .registrationId(ClientType.GOOGLE.getClientId())
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
}
