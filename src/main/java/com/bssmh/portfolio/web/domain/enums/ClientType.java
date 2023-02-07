package com.bssmh.portfolio.web.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientType {

    GOOGLE("google"),
    KAKAO("kakao"),
    BSM("bsm"),
    ;

    private final String clientId;

    public boolean isEqualToClientId(String clientId) {
        return this.clientId.equalsIgnoreCase(clientId);
    }
}
