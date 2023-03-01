package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.MemberRoleType;
import com.bssmh.portfolio.db.enums.MemberType;
import com.bssmh.portfolio.db.enums.PortfolioType;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.file.service.AttachFileService;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.AddPortfolioViewsCountRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.BookmarkPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioSequenceRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpsertPortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.exception.DoNotHavePermissionToModifyPortfolioException;
import com.bssmh.portfolio.web.exception.PortfolioEmptyException;
import com.bssmh.portfolio.web.exception.PortfolioSequenceException;
import com.bssmh.portfolio.web.exception.TeacherCannotCreatePortfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bssmh.portfolio.db.enums.MemberRoleType.ROLE_ADMIN;
import static com.bssmh.portfolio.db.enums.PortfolioType.URL;

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
        validationCheck(member, rq);
        Portfolio myLastSequencePortfolio = findPortfolioService.findMyLastSequencePortfolio(member.getId());
        int sequence = Objects.isNull(myLastSequencePortfolio) ? 1 : myLastSequencePortfolio.getSequence() + 1;

        AttachFile video = attachFileService.findByFileUidOrElseNull(rq.getVideoFileUid());
        AttachFile thumbnail = attachFileService.findByFileUidOrElseThrow(rq.getThumbnailFileUid());

        Portfolio portfolio = Portfolio.create(
                rq.getPortfolioType(),
                rq.getTitle(),
                rq.getDescription(),
                video,
                thumbnail,
                rq.getPortfolioUrl(),
                rq.getGitUrl(),
                rq.getPortfolioScope(),
                rq.getPortfolioTheme(),
                sequence,
                member);
        portfolioRepository.save(portfolio);

        upsertRelationShip(rq, portfolio);
    }

    private void validationCheck(Member member, UpsertPortfolioRq rq) {
        if (MemberType.TEACHER.equals(member.getMemberType())){
            throw new TeacherCannotCreatePortfolio();
        }

        PortfolioType portfolioType = rq.getPortfolioType();
        if (URL.equals(portfolioType) && !StringUtils.hasText(rq.getPortfolioUrl())) {
            throw new PortfolioEmptyException("Portfolio URL");
        }
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
        validationCheck(member, rq);
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
                rq.getPortfolioTheme(),
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

    public void updatePortfolioSequence(MemberContext memberContext, UpdatePortfolioSequenceRq rq) {
        List<Long> portfolioIdList = rq.getPortfolioIdList();
        if (ObjectUtils.isEmpty(portfolioIdList)) {
            return;
        }

        Member loginMember = findMemberService.findLoginMember(memberContext);
        List<Portfolio> portfolioList = loginMember.getPortfolioList();

        updatePortfolioSequenceValidationCheck(portfolioList, portfolioIdList);
        Map<Long, Portfolio> portfolioMap = portfolioList.stream()
                .collect(Collectors.toMap(Portfolio::getId, Function.identity()));

        for (int index = 0; index < portfolioIdList.size(); ++index) {
            Long portfolioId = portfolioIdList.get(index);
            Portfolio portfolio = portfolioMap.get(portfolioId);
            portfolio.updateSequence(index);
        }
    }

    private void updatePortfolioSequenceValidationCheck(List<Portfolio> portfolioList, List<Long> portfolioIdList) {
        Set<Long> portfolioIdSet = portfolioList.stream()
                .map(Portfolio::getId)
                .collect(Collectors.toSet());

        if (portfolioIdSet.size() != portfolioIdList.size()) {
            throw new PortfolioSequenceException();
        }

        portfolioIdList.forEach(portfolioId -> {
            if (!portfolioIdSet.contains(portfolioId)) {
                throw new PortfolioSequenceException();
            }
        });
    }

    public void addPortfolioViewsCount(AddPortfolioViewsCountRq rq) {
        Portfolio portfolio = findPortfolioService.findByIdOrElseThrow(rq.getPortfolioId());
        portfolio.addViewsCount();
    }
}
