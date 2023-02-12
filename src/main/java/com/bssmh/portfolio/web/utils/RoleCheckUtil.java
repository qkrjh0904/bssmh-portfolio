package com.bssmh.portfolio.web.utils;

import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.web.exception.AccessDeniedException;
import lombok.experimental.UtilityClass;

import java.util.Set;

import static com.bssmh.portfolio.db.enums.MemberRoleType.*;

@UtilityClass
public class RoleCheckUtil {

    public static void moreThanMember(MemberRoleType role) {
        Set<MemberRoleType> set = Set.of(ROLE_MEMBER, ROLE_ADMIN);
        roleCheck(set, role);
    }

    public static void moreThanAdmin(MemberRoleType role) {
        Set<MemberRoleType> set = Set.of(ROLE_ADMIN);
        roleCheck(set, role);
    }

    private static void roleCheck(Set<MemberRoleType> set, MemberRoleType role) {
        if(set.contains(role)){
            return;
        }
        throw new AccessDeniedException();
    }

}
