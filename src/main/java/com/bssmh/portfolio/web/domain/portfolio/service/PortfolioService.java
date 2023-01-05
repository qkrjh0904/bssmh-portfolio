package com.bssmh.portfolio.web.domain.portfolio.service;

import com.bssmh.portfolio.db.entity.attachfile.AttachFile;
import com.bssmh.portfolio.db.entity.member.Member;
import com.bssmh.portfolio.db.entity.portfolio.Portfolio;
import com.bssmh.portfolio.web.config.security.context.MemberContext;
import com.bssmh.portfolio.web.domain.file.service.AttachFileService;
import com.bssmh.portfolio.web.domain.member.service.FindMemberService;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.DeletePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.SavePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.controller.rq.UpdatePortfolioRq;
import com.bssmh.portfolio.web.domain.portfolio.repository.PortfolioRepository;
import com.bssmh.portfolio.web.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    // service
    private final FindMemberService findMemberService;
    private final AttachFileService attachFileService;
    private final PortfolioSkillService portfolioSkillService;
    private final ContributorService contributorService;

    // repository
    private final PortfolioRepository portfolioRepository;

    public void savePortfolio(MemberContext memberContext, SavePortfolioRq rq) {
        String email = memberContext.getEmail();
        Member member = findMemberService.findByEmail(email);
        if(Objects.isNull(member)){
            throw new NoSuchMemberException();
        }

        AttachFile video = attachFileService.findByFileUid(rq.getVideoFileUid());
        AttachFile thumbnail = attachFileService.findByFileUid(rq.getThumbnailFileUid());

        Portfolio portfolio = Portfolio.create(
                rq.getPortfolioType(),
                rq.getTitle(),
                rq.getDescription(),
                video,
                thumbnail,
                rq.getPortfolioUrl(),
                rq.getGitUrl(),
                rq.getPortfolioScope(),
                member);
        portfolioRepository.save(portfolio);

        saveRelationShip(rq, portfolio);
    }

    private void saveRelationShip(SavePortfolioRq rq, Portfolio portfolio) {
        portfolioSkillService.save(rq.getSkillList(), portfolio);
        contributorService.save(rq.getContributorIdList(), portfolio);
    }

    public void deletePortfolio(DeletePortfolioRq rq) {

    }

    public void updatePortfolio(UpdatePortfolioRq rq) {

    }
}
