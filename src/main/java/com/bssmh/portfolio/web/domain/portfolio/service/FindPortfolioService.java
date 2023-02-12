package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.follow.repository.service.FindFollowService;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final FindMemberService findMemberService;

    public FindPortfolioDetailRs findPortfolio(MemberContext memberContext, Long portfolioId) {
        Portfolio portfolio = findByIdOrElseThrow(portfolioId);
        validationCheck(memberContext, portfolio);
        return FindPortfolioDetailRs.create(portfolio);
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


    public PagedResponse<FindPortfolioListRs> searchPortfolioList(Pagination pagination, String search) {
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> portfolioListBySearch = portfolioRepository.findPortfolioListBySearch(search, pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = portfolioListBySearch.getContent().stream()
                .map(FindPortfolioListRs::create)
                .collect(Collectors.toList());

        pagination.setTotalCount(portfolioListBySearch.getTotalElements());
        pagination.setTotalPages(portfolioListBySearch.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

    public PagedResponse<FindPortfolioListRs> findMyPortfolio(MemberContext memberContext, Pagination pagination) {
        Member member = findMemberService.findLoginMember(memberContext);
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> myPortfolioList = portfolioRepository.findMyPortfolio(member.getId(), pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = myPortfolioList.stream()
                .map(FindPortfolioListRs::create)
                .collect(Collectors.toList());

        pagination.setTotalCount(myPortfolioList.getTotalElements());
        pagination.setTotalPages(myPortfolioList.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

    public PagedResponse<FindPortfolioListRs> findMemberPortfolio(Long memberId, Pagination pagination) {
        Member member = findMemberService.findByIdOrElseThrow(memberId);
        PageRequest pageRequest = pagination.toPageRequest();
        Page<Portfolio> memberPortfolioList = portfolioRepository.findMemberPortfolio(member.getId(), pageRequest);
        List<FindPortfolioListRs> findPortfolioListRsList = memberPortfolioList.stream()
                .map(FindPortfolioListRs::create)
                .collect(Collectors.toList());

        pagination.setTotalCount(memberPortfolioList.getTotalElements());
        pagination.setTotalPages(memberPortfolioList.getTotalPages());
        return PagedResponse.create(pagination, findPortfolioListRsList);
    }

}
