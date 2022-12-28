package com.bssmh.portfolio.web.security;

import lombok.Getter;

@Getter
public enum ClientType {

    NAVER("naver"),
    GOOGLE("google");

    private final String clientId;

    ClientType(String clientId) {
        this.clientId = clientId;
    }

}
