package com.bssmh.portfolio.db.entity.portfolio;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.contributor.Contributor;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.db.enums.PortfolioType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PortfolioType portfolioType;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private AttachFile video;

    @OneToOne(fetch = FetchType.EAGER)
    private AttachFile thumbnail;

    @Column(columnDefinition = "text")
    private String portfolioUrl;

    @Column(columnDefinition = "text")
    private String gitUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PortfolioScope portfolioScope;

    @Column(nullable = false)
    private Long views;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contributor> contributorList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioSkill> portfolioSkillList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    public static Portfolio create(PortfolioType portfolioType, String title, String description,
                                   AttachFile video, AttachFile thumbnail, String portfolioUrl, String gitUrl,
                                   PortfolioScope portfolioScope, Member member) {
        Portfolio portfolio = new Portfolio();
        portfolio.portfolioType = portfolioType;
        portfolio.title = title;
        portfolio.description = description;
        portfolio.video = video;
        portfolio.thumbnail = thumbnail;
        portfolio.portfolioUrl = portfolioUrl;
        portfolio.gitUrl = gitUrl;
        portfolio.portfolioScope = portfolioScope;
        portfolio.views = 0L;
        portfolio.member = member;
        return portfolio;
    }
}
