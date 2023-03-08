package com.bssmh.portfolio.db.entity.comment;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public static Comment create(String content, Portfolio portfolio, Member member, Comment parent) {
        Comment comment = new Comment();
        comment.content = content;
        comment.portfolio = portfolio;
        comment.member = member;
        comment.parent = parent;
        return comment;
    }

    public void update(String content) {
        this.content = content;
    }

}
