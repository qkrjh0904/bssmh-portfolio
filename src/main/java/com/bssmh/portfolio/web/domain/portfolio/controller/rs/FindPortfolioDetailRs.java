package com.bssmh.portfolio.web.domain.portfolio.controller.rs;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.db.entity.contributor.Contributor;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.entity.portfolio.PortfolioSkill;
import com.bssmh.portfolio.db.enums.PortfolioRecommendStatus;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.db.enums.PortfolioTheme;
import com.bssmh.portfolio.db.enums.PortfolioType;
import com.bssmh.portfolio.web.domain.dto.AttachFileDto;
import com.bssmh.portfolio.web.domain.dto.MemberDto;
import com.bssmh.portfolio.web.domain.dto.PortfolioSkillDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FindPortfolioDetailRs {

    @Schema(description = "포트폴리오 id")
    private Long portfolioId;

    @Schema(description = "작성자")
    private MemberDto writer;

    @Schema(description = "포트폴리오 타입")
    private PortfolioType portfolioType;

    @Schema(description = "포트폴리오 테마")
    private PortfolioTheme portfolioTheme;

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
    private List<PortfolioSkillDto> skillList;

    @Schema(description = "참여자 멤버 리스트")
    private List<MemberDto> contributorList;

    @Schema(description = "좋아요수")
    private Long bookmarks;

    @Schema(description = "좋아요 여부")
    private Boolean bookmarkYn;

    @Schema(description = "팔로우 여부")
    private Boolean followYn;

    @Schema(description = "조회수")
    private Long views;

    @Schema(description = "댓글수")
    private Long comments;

    @Schema(description = "추천 여부")
    private PortfolioRecommendStatus recommendStatus;

    @Schema(description = "생성일", pattern = "yyyy-MM-ddThh:mm:ss")
    private LocalDateTime createdDate;

    public static FindPortfolioDetailRs create(Portfolio portfolio, Boolean bookmarkYn, Boolean followYn) {
        FindPortfolioDetailRs rs = new FindPortfolioDetailRs();
        rs.portfolioId = portfolio.getId();
        rs.writer = getWriter(portfolio.getMember());
        rs.portfolioType = portfolio.getPortfolioType();
        rs.portfolioTheme = portfolio.getPortfolioTheme();
        rs.title = portfolio.getTitle();
        rs.description = portfolio.getDescription();
        rs.video = getVideo(portfolio.getVideo());
        rs.portfolioUrl = portfolio.getPortfolioUrl();
        rs.thumbnail = getThumbnail(portfolio.getThumbnail());
        rs.scope = portfolio.getPortfolioScope();
        rs.gitUrl = portfolio.getGitUrl();
        rs.skillList = getSkillList(portfolio.getPortfolioSkillList());
        rs.contributorList = getContributorList(portfolio.getContributorList());
        rs.bookmarks = getBookmarks(portfolio);
        rs.bookmarkYn = bookmarkYn;
        rs.followYn = followYn;
        rs.views = portfolio.getViews();
        rs.comments = getComments(portfolio);
        rs.recommendStatus = portfolio.getRecommendStatus();
        rs.createdDate = portfolio.getCreatedDate();
        return rs;
    }

    private static AttachFileDto getVideo(AttachFile video) {
        if (Objects.isNull(video)) {
            return null;
        }
        return AttachFileDto.create(video);
    }

    private static Long getComments(Portfolio portfolio) {
        return (long) portfolio.getCommentList().size();
    }

    private static Long getBookmarks(Portfolio portfolio) {
        return (long) portfolio.getBookmarkList().size();
    }

    private static List<MemberDto> getContributorList(List<Contributor> contributorList) {
        return contributorList.stream()
                .map(Contributor::getMember)
                .map(MemberDto::create)
                .collect(Collectors.toList());
    }

    private static List<PortfolioSkillDto> getSkillList(List<PortfolioSkill> portfolioSkillList) {
        return portfolioSkillList.stream()
                .map(PortfolioSkillDto::create)
                .collect(Collectors.toList());
    }

    private static AttachFileDto getThumbnail(AttachFile thumbnail) {
        return AttachFileDto.create(thumbnail);
    }

    private static MemberDto getWriter(Member member) {
        return MemberDto.create(member);
    }
}
