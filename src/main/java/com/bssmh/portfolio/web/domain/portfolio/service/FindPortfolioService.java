package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.bookmark.Bookmark;
import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.FollowRepository;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioDetailRs;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioListRs;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.endpoint.PagedResponse;
import com.bssmh.portfolio.web.endpoint.Pagination;
import com.bssmh.portfolio.web.exception.AccessDeniedException;
import com.bssmh.portfolio.web.exception.NoSuchPortfolioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final FindMemberService findMemberService;
    private final FindBookmarkService findBookmarkService;
    private final FollowRepository followRepository;

    public FindPortfolioDetailRs findPortfolio(MemberContext memberContext, Long portfolioId) {
        Portfolio portfolio = findByIdOrElseThrow(portfolioId);
        validationCheck(memberContext, portfolio);
        Boolean bookmarkYn = getBookmarkYn(memberContext, portfolio);
        Boolean followYn = getFollowYn(memberContext, portfolio.getMember());
        return FindPortfolioDetailRs.create(portfolio, bookmarkYn, followYn);
    }

    private Boolean getBookmarkYn(MemberContext memberContext, Portfolio portfolio) {
        Set<Long> bookmarkedPortfolioIdSet = getMyBookmarkedPortfolioIdSet(memberContext);
        return bookmarkedPortfolioIdSet.contains(portfolio.getId());
    }

    private Boolean getFollowYn(MemberContext memberContext, Member writer) {
        if (Objects.isNull(memberContext)) {
            return false;
        }

        Member loginMember = findMemberService.findLoginMember(memberContext);
        Follow follow = followRepository.findByFromMemberAndToMember(loginMember, writer).orElse(null);
        return Objects.nonNull(follow);
    }

    /**
     * 자신의 글이 아니라면 protected 까지 공개
     */
    private void validationCheck(MemberContext memberContext, Portfolio portfolio) {
        // 로그인 안했으면 public 만 가능
        if (Objects.isNull(memberContext)) {
            if (PortfolioScope.getMoreThanProtected(portfolio.getPortfolioScope())) {
                return;
            }
            throw new AccessDeniedException();
        }

        Member writer = portfolio.getMember();
        Member loginMember = findMemberService.findLoginMember(memberContext);

        // 내가 작성했으면 통과
        if (loginMember.getId().equals(writer.getId())) {
            return;
        }

        throw new AccessDeniedException();
    }

    public Portfolio findByIdOrElseThrow(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(NoSuchPortfolioException::new);
    }


    public PagedResponse<FindPortfolioListRs> searchPortfolioList(MemberContext memberContext, Pagination pagination, String search) {
        Set<Long> bookmarkedPortfolioIdSet = getMyBookmarkedPortfolioIdSet(memberContext);
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> portfolioListBySearch = portfolioRepository.findPortfolioListBySearch(search, pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = portfolioListBySearch.getContent().stream()
                .map(portfolio -> FindPortfolioListRs.create(portfolio, bookmarkedPortfolioIdSet))
                .collect(Collectors.toList());

        pagination.setTotalCount(portfolioListBySearch.getTotalElements());
        pagination.setTotalPages(portfolioListBySearch.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

    private Set<Long> getMyBookmarkedPortfolioIdSet(MemberContext memberContext) {
        if (Objects.isNull(memberContext)) {
            return new HashSet<>();
        }

        Member loginMember = findMemberService.findLoginMember(memberContext);
        return findBookmarkService.findByMember(loginMember).stream()
                .map(Bookmark::getPortfolio)
                .map(Portfolio::getId)
                .collect(Collectors.toSet());
    }

    public PagedResponse<FindPortfolioListRs> findMyPortfolio(MemberContext memberContext, Pagination pagination) {
        Member member = findMemberService.findLoginMember(memberContext);
        Set<Long> bookmarkedPortfolioIdSet = getMyBookmarkedPortfolioIdSet(member);
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> myPortfolioList = portfolioRepository.findMyPortfolio(member.getId(), pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = myPortfolioList.stream()
                .map(portfolio -> FindPortfolioListRs.create(portfolio, bookmarkedPortfolioIdSet))
                .collect(Collectors.toList());

        pagination.setTotalCount(myPortfolioList.getTotalElements());
        pagination.setTotalPages(myPortfolioList.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

    public PagedResponse<FindPortfolioListRs> findMemberPortfolio(MemberContext memberContext, Long memberId, Pagination pagination) {
        Member member = findMemberService.findByIdOrElseThrow(memberId);
        Set<Long> bookmarkedPortfolioIdSet = getMyBookmarkedPortfolioIdSet(memberContext);
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> memberPortfolioList = portfolioRepository.findMemberPortfolio(member.getId(), pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = memberPortfolioList.stream()
                .map(portfolio -> FindPortfolioListRs.create(portfolio, bookmarkedPortfolioIdSet))
                .collect(Collectors.toList());

        pagination.setTotalCount(memberPortfolioList.getTotalElements());
        pagination.setTotalPages(memberPortfolioList.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

    private Set<Long> getMyBookmarkedPortfolioIdSet(Member loginMember) {
        return findBookmarkService.findByMember(loginMember).stream()
                .map(Bookmark::getId)
                .collect(Collectors.toSet());
    }

    public Portfolio findMyLastSequencePortfolio(Long memberId) {
        return portfolioRepository.findMyLastSequencePortfolio(memberId)
                .orElse(null);
    }
}
