package com.bssmh.portfolio.web.domain.skill.service;

import com.bssmh.portfolio.web.domain.skill.controller.rs.FindSkillByNameRs;
import com.bssmh.portfolio.web.domain.skill.repository.SkillRepository;
import com.bssmh.portfolio.web.endpoint.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {

    private final SkillRepository skillRepository;


    public ListResponse<FindSkillByNameRs> findSkillByName(String name) {
        List<FindSkillByNameRs> findSkillByNameRsList = skillRepository.findSkillByName(name).stream()
                .map(FindSkillByNameRs::create)
                .collect(Collectors.toList());

        return ListResponse.create(findSkillByNameRsList);
    }
}
