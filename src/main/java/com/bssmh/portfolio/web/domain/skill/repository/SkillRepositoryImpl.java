package com.bssmh.portfolio.web.domain.skill.repository;

import com.bssmh.portfolio.db.entity.skill.Skill;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bssmh.portfolio.db.entity.skill.QSkill.skill;

@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Skill> findSkillByName(String name) {
        return jpaQueryFactory
                .selectFrom(skill)
                .where(nameEq(name))
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        if (StringUtils.hasText(name)) {
            return skill.name.contains(name);
        }
        return null;
    }

}
