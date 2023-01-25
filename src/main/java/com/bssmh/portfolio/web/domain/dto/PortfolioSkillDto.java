package com.bssmh.portfolio.web.domain.dto;

import com.bssmh.portfolio.db.entity.portfolio.PortfolioSkill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioSkillDto {

    @Schema(description = "기술스택 id")
    private Long skillId;

    @Schema(description = "기술스택명")
    private String skillName;

    public static PortfolioSkillDto create(PortfolioSkill portfolioSkill) {
        PortfolioSkillDto dto = new PortfolioSkillDto();
        dto.skillId = portfolioSkill.getSkillId();
        dto.skillName = portfolioSkill.getSkillName();
        return dto;
    }
}
