package com.bssmh.portfolio.web.domain.skill.repository;

import com.bssmh.portfolio.db.entity.skill.Skill;

import java.util.List;

public interface SkillRepositoryCustom {
    List<Skill> findSkillByName(String name);
}
