package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.issue.domain.SolutionDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.issue.repository.SolutionRepository;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.RuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SolutionService {

    private final IssueRepository issueRepository;
    private final SolutionRepository solutionRepository;
    private final RuleRepository ruleRepository;

    public SolutionService(SolutionRepository solutionRepository, IssueRepository issueRepository, RuleRepository ruleRepository) {
        this.solutionRepository = solutionRepository;
        this.issueRepository = issueRepository;
        this.ruleRepository = ruleRepository;
    }

    // 에러 해결 등록
    @Transactional
    public Solution createSolution(SolutionDTO.RequestDTO solutionRequestDTO, Long issueId){
        Optional<Issue> optionalIssue = issueRepository.findById(issueId);
        if (optionalIssue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이슈 없음");
        }
        Issue issue = optionalIssue.get();
        
        Duration duration = getDuration(issue);  // 시간 차이
        int amount = getAmount(duration, issue);
        issue.setAmount(amount); // 금액 설정
        issue.setPublicStatus(solutionRequestDTO.getPublicStatus());  // 공개 여부 설정

        return solutionRepository.save(solutionRequestDTO.toEntity(issue));
    }

    // 시간 차이 계산
    private static Duration getDuration(Issue issue) {
        LocalDateTime solutionCreatedAt = LocalDateTime.now();
        LocalDateTime issueCreatedAt = issue.getCreatedAt();
        return Duration.between(issueCreatedAt, solutionCreatedAt);
    }

    // 금액 계산
    private int getAmount(Duration duration, Issue issue) {
        long timeDiff = duration.toMillis(); // 분 단위로 계산

        // 규칙에서 사용자가 설정한 금액
        Rule rule = ruleRepository.findByUsersId(issue.getUsers().getId());
        if(timeDiff < 30){
            return rule.getUnderThirty();
        } else if (timeDiff < 60) {
            return rule.getUnderHour();
        } else if (timeDiff < 180) {
            return rule.getUnderThreeHour();
        } else {
            return rule.getOverThreeHour();
        }
    }

}
