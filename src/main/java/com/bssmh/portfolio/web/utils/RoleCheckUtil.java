package com.bssmh.portfolio.web.utils;

import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.exception.AccessDeniedException;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class RoleCheckUtil {

    public static void moreThanMember(MemberContext memberContext) {
        if (Objects.isNull(memberContext)) {
            throw new AccessDeniedException();
        }

        MemberRoleType role = memberContext.getRole();
        if (MemberRoleType.ROLE_NORMAL.equals(role)) {
            throw new AccessDeniedException();
        }
    }

}
