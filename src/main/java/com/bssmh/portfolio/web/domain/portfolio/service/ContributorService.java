package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.contributor.Contributor;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.domain.member.service.MemberService;
import com.bssmh.portfolio.web.domain.portfolio.repository.ContributorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContributorService {

    private final MemberService memberService;
    private final ContributorRepository contributorRepository;

    public void save(List<Long> contributorIdList, Portfolio portfolio) {
        if(ObjectUtils.isEmpty(contributorIdList)){
            return;
        }

        List<Member> memberList = memberService.findAllByIdList(contributorIdList);
        List<Contributor> contributorList = memberList.stream()
                .map(member -> Contributor.create(portfolio, member))
                .collect(Collectors.toList());
        contributorRepository.saveAll(contributorList);
    }
}
