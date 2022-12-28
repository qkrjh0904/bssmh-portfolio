package com.bssmh.portfolio.web.domain.portfolio.controller.rs;

import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.db.enums.PortfolioType;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.dto.MemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class FindPortfolioDetailRs {

    @Schema(description = "포트폴리오 id")
    private Long portfolioId;

    @Schema(description = "작성자")
    private MemberDto writer;

    @Schema(description = "포트폴리오 타입")
    private PortfolioType portfolioType;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "포트폴리오 영상 파일")
    private AttachFileDto video;

    @Schema(description = "포트폴리오 url")
    private String portfolioUrl;

    @Schema(description = "포트폴리오 영상 파일")
    private AttachFileDto thumbnail;

    @Schema(description = "공개 범위")
    private PortfolioScope scope;

    @Schema(description = "포트폴리오 git url")
    private String gitUrl;

    @Schema(description = "기술스택 리스트")
    private List<String> skillList;

    @Schema(description = "참여자 멤버 리스트")
    private List<MemberDto> contributorList;

    @Schema(description = "좋아요수")
    private Long bookmarks;

    @Schema(description = "조회수")
    private Long views;

    @Schema(description = "댓글수")
    private Long comments;

    @Schema(description = "생성일", pattern = "yyyy-MM-dd")
    private String createdDate;
}
