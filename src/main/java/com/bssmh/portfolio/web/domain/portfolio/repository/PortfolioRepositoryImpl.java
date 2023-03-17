package com.bssmh.portfolio.web.domain.portfolio.repository;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.enums.PortfolioRecommendStatus;
import com.bssmh.portfolio.db.enums.PortfolioScope;
import com.bssmh.portfolio.db.enums.PortfolioSortType;
import com.bssmh.portfolio.db.enums.PortfolioTheme;
import com.bssmh.portfolio.db.enums.SearchType;
import com.bssmh.portfolio.db.enums.SortDirectionType;
import com.bssmh.portfolio.db.enums.UploadDateType;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SearchPortfolioFilterRq;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.bssmh.portfolio.db.entity.portfolio.QPortfolio.portfolio;
import static com.bssmh.portfolio.db.enums.PortfolioScope.PUBLIC;
import static com.bssmh.portfolio.db.enums.SortDirectionType.ASC;

@RequiredArgsConstructor
public class PortfolioRepositoryImpl implements PortfolioRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Portfolio> findPortfolioListBySearch(SearchPortfolioFilterRq filter, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(searchEq(filter.getSearch(), filter.getSearchType()),
                        uploadDateEq(filter.getUploadDateType()),
                        schoolGradeEq(filter.getSchoolGrade()),
                        portfolioThemeEq(filter.getPortfolioTheme()),
                        portfolioRecommendStatusEq(filter.getRecommendStatus()),
                        scopeEq(List.of(PUBLIC)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderBy(filter.getSortType(), filter.getSortDirectionType()))
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(searchEq(filter.getSearch(), filter.getSearchType()),
                        uploadDateEq(filter.getUploadDateType()),
                        schoolGradeEq(filter.getSchoolGrade()),
                        portfolioThemeEq(filter.getPortfolioTheme()),
                        portfolioRecommendStatusEq(filter.getRecommendStatus()),
                        scopeEq(List.of(PUBLIC)));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression portfolioRecommendStatusEq(PortfolioRecommendStatus recommendStatus) {
        if (Objects.isNull(recommendStatus)) {
            return null;
        }
        return portfolio.recommendStatus.eq(recommendStatus);
    }

    private BooleanExpression portfolioThemeEq(PortfolioTheme portfolioTheme) {
        if (Objects.isNull(portfolioTheme)) {
            return null;
        }
        return portfolio.portfolioTheme.eq(portfolioTheme);
    }

    private OrderSpecifier<?>[] getOrderBy(PortfolioSortType sortType, SortDirectionType sortDirectionType) {
        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();
        if (Objects.isNull(sortType)) {
            orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, portfolio.views).nullsLast());
            orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, portfolio.createdDate).nullsLast());
            return orderSpecifierList.toArray(OrderSpecifier<?>[]::new);
        }

        Order order = (Objects.nonNull(sortDirectionType) && ASC.equals(sortDirectionType)) ? Order.ASC : Order.DESC;

        switch (sortType) {
            case UPLOAD_DATE:
                orderSpecifierList.add(new OrderSpecifier<>(order, portfolio.createdDate).nullsLast());
                break;
            case VIEWS:
                orderSpecifierList.add(new OrderSpecifier<>(order, portfolio.views).nullsLast());
                break;
            case BOOKMARKS:
                orderSpecifierList.add(new OrderSpecifier<>(order, portfolio.bookmarkList.size()).nullsLast());
                break;
            case COMMENTS:
                orderSpecifierList.add(new OrderSpecifier<>(order, portfolio.commentList.size()).nullsLast());
                break;
        }

        orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, portfolio.views).nullsLast());
        orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, portfolio.createdDate).nullsLast());
        return orderSpecifierList.toArray(OrderSpecifier<?>[]::new);
    }

    private BooleanExpression schoolGradeEq(Integer schoolGrade) {
        if (Objects.isNull(schoolGrade)) {
            return null;
        }
        return portfolio.schoolGrade.eq(schoolGrade);
    }

    private BooleanExpression uploadDateEq(UploadDateType uploadDateType) {
        if (Objects.isNull(uploadDateType)) {
            return null;
        }
        switch (uploadDateType) {
            case AN_HOUR_AGO:
                return portfolio.createdDate.after(LocalDateTime.now().minusHours(1));
            case TODAY:
                return portfolio.createdDate.after(LocalDateTime.now().minusDays(1));
            case THIS_WEEK:
                return portfolio.createdDate.after(LocalDateTime.now().minusWeeks(1));
            case THIS_MONTH:
                return portfolio.createdDate.after(LocalDateTime.now().minusMonths(1));
            case THIS_YEAR:
                return portfolio.createdDate.after(LocalDateTime.now().minusYears(1));
            default:
                return null;
        }
    }

    @Override
    public Page<Portfolio> findMyPortfolio(Long memberId, Set<Long> contributedPortfolioIdSet, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId).or(portfolio.id.in(contributedPortfolioIdSet)))
                .orderBy(portfolio.sequence.asc(), portfolio.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(portfolio.member.id.eq(memberId).or(portfolio.id.in(contributedPortfolioIdSet)))
                .orderBy(portfolio.sequence.asc(), portfolio.createdDate.desc());

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Page<Portfolio> findMemberPortfolio(Long memberId, Collection<Long> contributedPortfolioIdSet, Pageable pageable) {
        List<Portfolio> contents = jpaQueryFactory
                .selectFrom(portfolio)
                .where(memberIdEq(memberId)
                        .or(contributedPortfolioIdSetEq(contributedPortfolioIdSet)))
                .orderBy(portfolio.sequence.asc(), portfolio.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Portfolio> countQuery = jpaQueryFactory
                .selectFrom(portfolio)
                .where(memberIdEq(memberId)
                        .or(contributedPortfolioIdSetEq(contributedPortfolioIdSet)))
                .orderBy(portfolio.sequence.asc(), portfolio.createdDate.desc());

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression contributedPortfolioIdSetEq(Collection<Long> contributedPortfolioIdSet) {
        return portfolio.id.in(contributedPortfolioIdSet).and(scopeEq(List.of(PUBLIC)));
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return portfolio.member.id.eq(memberId).and(scopeEq(List.of(PUBLIC)));
    }

    @Override
    public Optional<Portfolio> findMyLastSequencePortfolio(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(portfolio)
                        .where(portfolio.member.id.eq(memberId))
                        .orderBy(portfolio.sequence.desc())
                        .fetchFirst()
        );
    }

    private BooleanExpression scopeEq(List<PortfolioScope> portfolioScopeList) {
        if (ObjectUtils.isEmpty(portfolioScopeList)) {
            return null;
        }
        return portfolio.portfolioScope.in(portfolioScopeList);
    }

    private BooleanExpression searchEq(String search, SearchType searchType) {
        if (!StringUtils.hasText(search)) {
            return null;
        }
        switch (searchType) {
            case TITLE:
                return portfolio.title.contains(search);
            case THEME:
                PortfolioTheme theme = PortfolioTheme.valueOf(search);
                return portfolio.portfolioTheme.eq(theme);
            case CREATOR:
                return portfolio.member.name.contains(search);
            case CONTRIBUTOR:
                return portfolio.contributorList.any().member.name.contains(search);
            case CREATOR_AND_CONTRIBUTOR:
                return portfolio.member.name.contains(search)
                        .or(portfolio.contributorList.any().member.name.contains(search));
            default:
                return null;
        }
    }
}
