package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberListByName(Member loginMember, String name);
}
