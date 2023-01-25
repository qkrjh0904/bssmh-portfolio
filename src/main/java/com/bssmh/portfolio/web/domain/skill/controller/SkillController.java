package com.bssmh.portfolio.web.domain.skill.controller;

import com.bssmh.portfolio.web.domain.skill.controller.rs.FindSkillByNameRs;
import com.bssmh.portfolio.web.domain.skill.service.SkillService;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import com.bssmh.portfolio.web.path.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "기술스택")
@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;


    @Operation(summary = "기술명으로 기술스택 조회")
    @GetMapping(ApiPath.SKILL)
    public ListResponse<FindSkillByNameRs> findSkillByName(@RequestParam(required = false) String name) {
        return skillService.findSkillByName(name);
    }
}
