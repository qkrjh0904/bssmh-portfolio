package com.bssmh.portfolio.web.config.security;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthorizedToken extends AbstractAuthenticationToken {

    private final MemberContext memberContext;

    public AuthorizedToken(MemberContext memberContext) {
        super(null);
        this.memberContext = memberContext;
        super.setAuthenticated(true);
    }

    public static AuthorizedToken create(MemberContext memberContext){
        return new AuthorizedToken(memberContext);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return memberContext;
    }

}
