package com.bssmh.portfolio.web.domain.member.service;

import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.member.MemberClassInfo;
import com.bssmh.portfolio.db.enums.MemberType;
import com.bssmh.portfolio.web.domain.member.repository.MemberClassInfoRepository;
import com.bssmh.portfolio.web.exception.ClassInfoEmptyException;
import com.bssmh.portfolio.web.exception.ClassInfoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.bssmh.portfolio.db.enums.MemberType.TEACHER;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberClassInfoService {

    private final MemberClassInfoRepository memberClassInfoRepository;

    public MemberClassInfo findByMemberIdAndSchoolGradeOrElseNull(Long memberId, Integer schoolGrade) {
        return memberClassInfoRepository.findByMemberIdAndSchoolGrade(memberId, schoolGrade)
                .orElse(null);
    }

    public void upsert(MemberType memberType, MemberClassInfo memberClassInfo, Integer schoolGrade, Integer schoolClass,
                       Integer schoolNumber, Member member) {
        if (TEACHER.equals(memberType)) {
            return;
        }

        validationCheck(schoolGrade, schoolClass, schoolNumber);
        if (Objects.isNull(memberClassInfo)) {
            save(schoolGrade, schoolClass, schoolNumber, member);
            return;
        }

        memberClassInfo.update(schoolClass, schoolNumber);
    }

    private void save(Integer schoolGrade, Integer schoolClass, Integer schoolNumber, Member member) {
        MemberClassInfo memberClassInfo = MemberClassInfo.create(schoolGrade, schoolClass, schoolNumber, member);
        memberClassInfoRepository.save(memberClassInfo);
    }

    private void validationCheck(Integer schoolGrade, Integer schoolClass, Integer schoolNumber) {
        if (Objects.isNull(schoolGrade) || Objects.isNull(schoolClass) || Objects.isNull(schoolNumber)) {
            throw new ClassInfoEmptyException();
        }

        if (schoolGrade < 1 || schoolGrade > 3 || schoolClass < 1 || schoolNumber < 1) {
            throw new ClassInfoException();
        }
    }

}
