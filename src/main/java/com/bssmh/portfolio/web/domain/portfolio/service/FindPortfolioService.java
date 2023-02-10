package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.follow.Follow;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
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

import static com.bssmh.portfolio.db.enums.PortfolioScope.PRIVATE;
import static com.bssmh.portfolio.db.enums.PortfolioScope.PUBLIC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final FindMemberService findMemberService;
    private final FindFollowService findFollowService;

    public FindPortfolioDetailRs findPortfolio(MemberContext memberContext, Long portfolioId) {
        Portfolio portfolio = findByIdOrElseThrow(portfolioId);
        validationCheck(memberContext, portfolio);
        return FindPortfolioDetailRs.create(portfolio);
    }

    /**
     * 1. 자신의 글인가?
     * 2. 자신의 글이 아니고 PROTECTED 면 팔로우 했는가?
     * 3. 자신의 글이 아닌데 PRIVATE 아닌가?
     */
    private void validationCheck(MemberContext memberContext, Portfolio portfolio) {
        // 로그인 안했으면 public 만 가능
        if (Objects.isNull(memberContext)) {
            if (PUBLIC.equals(portfolio.getPortfolioScope())) {
                return;
            }
            throw new AccessDeniedException();
        }

        Member writer = portfolio.getMember();
        Member member = findMemberService.getLoginMember(memberContext);

        // 내가 작성했으면 통과
        if (member.getId().equals(writer.getId())) {
            return;
        }

        // private 는 접근 불가
        if (PRIVATE.equals(portfolio.getPortfolioScope())) {
            throw new AccessDeniedException();
        }

        // 팔로우면 protected 가능
        Follow follow = findFollowService.findByEachMemberOrElseNull(member, writer);
        if(Objects.isNull(follow)){
            throw new AccessDeniedException();
        }
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
        Member member = findMemberService.getLoginMember(memberContext);
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
