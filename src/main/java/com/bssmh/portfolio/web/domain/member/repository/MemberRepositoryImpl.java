package com.bssmh.portfolio.web.domain.member.repository;

import com.bssmh.portfolio.db.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bssmh.portfolio.db.entity.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findMemberListByName(Member loginMember, String name) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.id.notIn(loginMember.getId()),
                        member.name.contains(name))
                .fetch();
    }
}
