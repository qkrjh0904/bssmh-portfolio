package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.file.service.AttachFileService;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.BookmarkPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpsertPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.exception.DoNotHavePermissionToModifyPortfolioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bssmh.portfolio.db.enums.MemberRoleType.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    // service
    private final FindMemberService findMemberService;
    private final AttachFileService attachFileService;
    private final PortfolioSkillService portfolioSkillService;
    private final ContributorService contributorService;
    private final FindPortfolioService findPortfolioService;

    private final BookmarkService bookmarkService;

    // repository
    private final PortfolioRepository portfolioRepository;

    public void savePortfolio(MemberContext memberContext, UpsertPortfolioRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);

        AttachFile video = attachFileService.findByFileUidOrElseNull(rq.getVideoFileUid());
        AttachFile thumbnail = attachFileService.findByFileUidOrElseNull(rq.getThumbnailFileUid());

        Portfolio portfolio = Portfolio.create(
                rq.getPortfolioType(),
                rq.getTitle(),
                rq.getDescription(),
                video,
                thumbnail,
                rq.getPortfolioUrl(),
                rq.getGitUrl(),
                rq.getPortfolioScope(),
                member);
        portfolioRepository.save(portfolio);

        upsertRelationShip(rq, portfolio);
    }

    private void upsertRelationShip(UpsertPortfolioRq rq, Portfolio portfolio) {
        portfolioSkillService.upsert(rq.getSkillList(), portfolio);
        contributorService.upsert(rq.getContributorIdList(), portfolio);
    }

    public void deletePortfolio(MemberContext memberContext, DeletePortfolioRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Long portfolioId = rq.getPortfolioId();
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(portfolioId);
        portfolioPermissionCheck(portfolio, member);
        portfolioRepository.delete(portfolio);
    }

    public void updatePortfolio(MemberContext memberContext, UpsertPortfolioRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Long portfolioId = rq.getPortfolioId();
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(portfolioId);
        portfolioPermissionCheck(portfolio, member);

        AttachFile video = attachFileService.findByFileUidOrElseThrow(rq.getVideoFileUid());
        AttachFile thumbnail = attachFileService.findByFileUidOrElseThrow(rq.getThumbnailFileUid());

        portfolio.update(
                rq.getTitle(),
                rq.getDescription(),
                video,
                thumbnail,
                rq.getPortfolioUrl(),
                rq.getPortfolioScope(),
                rq.getPortfolioType(),
                rq.getGitUrl());

        upsertRelationShip(rq, portfolio);
    }

    private void portfolioPermissionCheck(Portfolio portfolio, Member member) {
        if (ROLE_ADMIN.equals(member.getMemberRoleType())) {
            return;
        }

        Long writerId = portfolio.getMember().getId();
        Long memberId = member.getId();
        if (writerId.equals(memberId)) {
            return;
        }
        throw new DoNotHavePermissionToModifyPortfolioException();
    }


    public void bookmarkPortfolio(MemberContext memberContext, BookmarkPortfolioRq rq) {
        Member member = findMemberService.findLoginMember(memberContext);
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(rq.getPortfolioId());
        bookmarkService.toggleBookmarkPortfolio(member, portfolio);
    }

}
