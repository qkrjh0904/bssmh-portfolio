package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.entity.portfolio.PortfolioSkill;
import com.bssmh.portfolio.web.domain.dto.PortfolioSkillDto;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioSkillService {

    private final PortfolioSkillRepository portfolioSkillRepository;

    public void upsert(List<PortfolioSkillDto> skillDtoList, Portfolio portfolio) {
        if (ObjectUtils.isEmpty(skillDtoList)) {
            return;
        }

        List<PortfolioSkill> portfolioSkillList = skillDtoList.stream()
                .map(it -> PortfolioSkill.create(it, portfolio))
                .collect(Collectors.toList());
        portfolio.upsertSkillList(portfolioSkillList);
    }
}
