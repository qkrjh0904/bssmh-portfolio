package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.db.entity.portfolio.PortfolioSkill;
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

    public void save(List<String> skillList, Portfolio portfolio) {
        if (ObjectUtils.isEmpty(skillList)) {
            return;
        }

        List<PortfolioSkill> portfolioSkillList = skillList.stream()
                .map(it -> PortfolioSkill.create(it, portfolio))
                .collect(Collectors.toList());
        portfolioSkillRepository.saveAll(portfolioSkillList);
    }
}
