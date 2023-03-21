package com.bssmh.portfolio.web.utils;

import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.exception.AuthenticationException;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class RoleCheckUtil {

    public static void loginCheck(MemberContext memberContext) {
        if (Objects.isNull(memberContext)) {
            throw new AuthenticationException();
        }
    }

}
