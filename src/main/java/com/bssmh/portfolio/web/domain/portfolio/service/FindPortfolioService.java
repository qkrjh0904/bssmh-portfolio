package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioDetailRs;
import com.bssmh.portfolio.web.domain.portfolio.controller.rs.FindPortfolioListRs;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.endpoint.PagedResponse;
import com.bssmh.portfolio.web.endpoint.Pagination;
import com.bssmh.portfolio.web.exception.NoSuchPortfolioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final FindMemberService findMemberService;

    public FindPortfolioDetailRs findPortfolio(Long portfolioId) {
        Portfolio portfolio = findByIdOrElseThrow(portfolioId);
        return FindPortfolioDetailRs.create(portfolio);
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
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmailOrElseThrow(email);
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
