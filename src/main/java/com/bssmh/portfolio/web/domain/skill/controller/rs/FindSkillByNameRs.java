package com.bssmh.portfolio.web.domain.skill.controller.rs;

import com.bssmh.portfolio.db.entity.skill.Skill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FindSkillByNameRs {

    @Schema(description = "기술스택 id")
    private Long skillId;

    @Schema(description = "기술스택명")
    private String skillName;

    public static FindSkillByNameRs create(Skill skill) {
        FindSkillByNameRs rs = new FindSkillByNameRs();
        rs.skillId = skill.getId();
        rs.skillName = skill.getName();
        return rs;
    }
}
