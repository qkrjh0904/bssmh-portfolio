package com.bssmh.portfolio.web.domain.portfolio.controller.rq;

import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.db.enums.PortfolioType;
import com.bssmh.portfolio.web.domain.dto.PortfolioSkillDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UpsertPortfolioRq {

    @Schema(description = "포트폴리오 id")
    private Long portfolioId;
    
    @Schema(description = "제목")
    private String title;
    
    @Schema(description = "설명")
    private String description;

    @Schema(description = "포트폴리오 영상 파일 uid")
    private String videoFileUid;

    @Schema(description = "포트폴리오 url")
    private String portfolioUrl;

    @Schema(description = "포트폴리오 썸네일 uid")
    private String thumbnailFileUid;

    @NotNull
    @Schema(description = "공개 범위")
    private PortfolioScope portfolioScope;

    @NotNull
    @Schema(description = "포트폴리오 게시 타입")
    private PortfolioType portfolioType;

    @Schema(description = "포트폴리오 git url")
    private String gitUrl;
    
    @Schema(description = "기술스택 리스트")
    private List<PortfolioSkillDto> skillList;

    @Schema(description = "참여자 멤버 id")
    private List<Long> contributorIdList;

}
