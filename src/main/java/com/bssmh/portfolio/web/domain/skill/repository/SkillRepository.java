package com.bssmh.portfolio.web.domain.skill.repository;

import com.bssmh.portfolio.db.entity.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long>, SkillRepositoryCustom {
}
