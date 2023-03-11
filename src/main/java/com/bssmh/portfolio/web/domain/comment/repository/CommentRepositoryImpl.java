package com.bssmh.portfolio.web.domain.comment.repository;
import com.bssmh.portfolio.db.entity.comment.Comment;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bssmh.portfolio.db.entity.comment.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findParentCommentByPortfolio(Portfolio portfolio){
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.portfolio.id.eq(portfolio.getId())
                        .and(comment.parent.isNull()))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.createdDate.asc())
                .fetch();
    }

}
