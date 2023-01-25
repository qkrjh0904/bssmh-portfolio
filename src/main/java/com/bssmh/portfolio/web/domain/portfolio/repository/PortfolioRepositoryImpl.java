package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bssmh.portfolio.db.entity.portfolio.QPortfolio.portfolio;
import static com.bssmh.portfolio.db.enums.PortfolioScope.PUBLIC;

@RequiredArgsConstructor
public class PortfolioRepositoryImpl implements PortfolioRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Portfolio> findPortfolioListBySearch(String search, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(searchEq(search),
                        scopeEq(List.of(PUBLIC)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(portfolio.views.desc(), portfolio.createdDate.desc())
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(searchEq(search),
                        scopeEq(List.of(PUBLIC)));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Portfolio> findMyPortfolio(Long memberId, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(portfolio.createdDate.desc())
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Portfolio> findMemberPortfolio(Long memberId, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId),
                        scopeEq(List.of(PUBLIC)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(portfolio.createdDate.desc())
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId),
                        scopeEq(List.of(PUBLIC)));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression scopeEq(List<PortfolioScope> portfolioScopeList) {
        if (ObjectUtils.isEmpty(portfolioScopeList)) {
            return null;
        }
        return portfolio.portfolioScope.in(portfolioScopeList);
    }

    private BooleanExpression searchEq(String search) {
        if (StringUtils.hasText(search)) {
            return portfolio.title.contains(search);
        }
        return null;
    }
}
