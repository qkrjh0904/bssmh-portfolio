package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
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

    public FindPortfolioDetailRs findPortfolio(Long portfolioId) {
        Portfolio portfolio = findById(portfolioId);
        return FindPortfolioDetailRs.create(portfolio);
    }

    private Portfolio findById(Long portfolioId) {
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

    public PagedResponse<FindPortfolioListRs> findMyPortfolioSelf(MemberContext memberContext, Pagination pagination) {
        String email = memberContext.getEmail();
        return null;
    }

    public PagedResponse<FindPortfolioListRs> findMemberPortfolio(Long memberId, Pagination pagination) {
        return null;
    }
}
