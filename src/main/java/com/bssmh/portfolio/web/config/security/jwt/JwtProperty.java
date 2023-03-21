package com.bssmh.portfolio.web.config.security.jwt;

public class JwtProperty {

    public static final String SIGN_KEY = "bssm-sign-key";
    public static final String MEMBER_EMAIL = "MEMBER_EMAIL";
    public static final String REGISTRATION_ID = "REGISTRATION_ID";
    public static final String MEMBER_ROLE_TYPE = "MEMBER_ROLE_TYPE";
    public static final String JWT_ISSUER = "BSSMH";
    public static final Long TOKEN_TIME_TO_LIVE = 24 * 60 * 60 * 1000L;
    public static final String JWT_EXCEPTION = "exception";
    public static final String TOKEN_KEY = "token";
}
