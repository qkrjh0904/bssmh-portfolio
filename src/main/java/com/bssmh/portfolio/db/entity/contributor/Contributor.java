package com.bssmh.portfolio.db.entity.contributor;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contributor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contributor_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

}
