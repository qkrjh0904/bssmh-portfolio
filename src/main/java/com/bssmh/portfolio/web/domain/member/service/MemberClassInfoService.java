package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberClassInfo;
import com.bssmh.portfolio.web.domain.member.repository.MemberClassInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberClassInfoService {

    private final MemberClassInfoRepository memberClassInfoRepository;

    public void save(Integer schoolGrade, Integer schoolClass, Integer schoolNumber, Member member) {
        MemberClassInfo memberClassInfo = MemberClassInfo.create(schoolGrade, schoolClass, schoolNumber, member);
        memberClassInfoRepository.save(memberClassInfo);
    }

    public MemberClassInfo findByMemberIdAndSchoolGradeOrElseNull(Long memberId, Integer schoolGrade) {
        return memberClassInfoRepository.findByMemberIdAndSchoolGrade(memberId, schoolGrade)
                .orElse(null);
    }

    public void upsert(MemberClassInfo memberClassInfo, Integer schoolGrade, Integer schoolClass,
                       Integer schoolNumber, Member member) {
        if (Objects.isNull(memberClassInfo)) {
            this.save(schoolGrade, schoolClass, schoolNumber, member);
            return;
        }

        memberClassInfo.update(schoolClass, schoolNumber);
    }
}
