package com.bssmh.portfolio.db.entity.portfolio;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.web.domain.dto.PortfolioSkillDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioSkill extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_skill_id")
    private Long id;

    private Long skillId;

    @Column(nullable = false)
    private String skillName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;


    public static PortfolioSkill create(PortfolioSkillDto dto, Portfolio portfolio) {
        PortfolioSkill portfolioSkill = new PortfolioSkill();
        portfolioSkill.skillId = dto.getSkillId();
        portfolioSkill.skillName = dto.getSkillName();
        portfolioSkill.portfolio = portfolio;
        return portfolioSkill;
    }
}
