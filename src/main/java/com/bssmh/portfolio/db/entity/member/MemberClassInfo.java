package com.bssmh.portfolio.db.entity.member;

import com.bssmh.portfolio.db.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberClassInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_class_info_id")
    private Long id;

    private Integer schoolGrade;

    private Integer schoolClass;

    private Integer schoolNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    public static MemberClassInfo create(Integer schoolGrade, Integer schoolClass, Integer schoolNumber, Member member) {
        MemberClassInfo memberClassInfo = new MemberClassInfo();
        memberClassInfo.schoolGrade = schoolGrade;
        memberClassInfo.schoolClass = schoolClass;
        memberClassInfo.schoolNumber = schoolNumber;
        memberClassInfo.member = member;
        return memberClassInfo;
    }

    public void update(Integer schoolGrade, Integer schoolClass, Integer schoolNumber) {
        this.schoolGrade = schoolGrade;
        this.schoolClass = schoolClass;
        this.schoolNumber = schoolNumber;
    }
}
