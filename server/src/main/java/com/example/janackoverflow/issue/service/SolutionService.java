package com.example.janackoverflow.issue.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.issue.domain.SolutionDTO;
import com.example.janackoverflow.issue.domain.request.CreateSolutionRequestDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.domain.response.SolutionResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.issue.repository.SolutionRepository;
import com.example.janackoverflow.saving.domain.response.MonthlyCountIssueDTO;
import com.example.janackoverflow.main.service.BankingService;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.saving.repository.RuleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    private final IssueRepository issueRepository;
    private final SolutionRepository solutionRepository;
    private final RuleRepository ruleRepository;
    private final InputAccountRepository inputAccountRepository;

    private final BankingService bankingService;

    public SolutionService(SolutionRepository solutionRepository, IssueRepository issueRepository, RuleRepository ruleRepository, InputAccountRepository inputAccountRepository, BankingService bankingService) {
        this.solutionRepository = solutionRepository;
        this.issueRepository = issueRepository;
        this.ruleRepository = ruleRepository;
        this.inputAccountRepository = inputAccountRepository;
        this.bankingService = bankingService;
    }

    // 에러 해결 등록
    @Transactional
    public Solution createSolution(CreateSolutionRequestDTO solutionRequestDTO, Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));

        Duration duration = getDuration(issue);  // 시간
        int amount = getAmount(duration, issue);  // 금액
        issue.updateAmount(amount);  // 금액 변경
        issue.updatePublicStatus(solutionRequestDTO.getPublicStatus());  // 공개 여부
        issue.updateStatus("03");  // 상태 (해결: 03)

        // 현재 진행 중인(01) 계좌에 +amount
        InputAccount inputAccount = inputAccountRepository.findByUsersIdAndStatus(issue.getUsers().getId(), "01")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));
        inputAccount.updateAcntAmount(inputAccount.getAcntAmount() + amount);

        //입금
        try {
            bankingService.transfer(issue.getAmount(), inputAccount.getAcntNum());
        } catch (JsonProcessingException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        // 적금 만료 (계좌 금액 >= 목표 금액)일 경우, status(03)와 completed_at 변경
        if (inputAccount.getAcntAmount() >= inputAccount.getGoalAmount()) {
            inputAccount.updateStatus("03");  // 완료 상태로 변경
            inputAccount.updateCompletedAt();  // 완료 일자 설정
        }
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
        long timeDiff = duration.toMinutes(); // 분 단위로 계산
        // 규칙에서 사용자가 설정한 금액
        Optional<InputAccount> inputAccountId = inputAccountRepository.findByUsersIdAndStatus(issue.getUsers().getId(), "01");
        Rule rule = ruleRepository.findByInputAccountId(inputAccountId
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND))
                .getId());

        if (timeDiff < 30) {
            return rule.getUnderThirty();
        } else if (timeDiff < 60) {
            return rule.getUnderHour();
        } else if (timeDiff < 180) {
            return rule.getUnderThreeHour();
        } else {
            return rule.getOverThreeHour();
        }
    }

    public SolutionDTO getSolution(long issueId) {
        Solution solution = solutionRepository.findByIssue_Id(issueId).orElseThrow(() -> new IllegalArgumentException("해당 해결을 찾을 수 없습니다."));
        return solution.toDto();
    }

    // 최근 해결 조회
    @Transactional(readOnly = true)
    public SolutionResponseDTO getRecentSolution(Long solutionId) {
        Solution solution = solutionRepository.findById(solutionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));
        return SolutionResponseDTO.toDto(solution);
    }

    // 년도 월에 해당하는 해결 조회
    @Transactional(readOnly = true)
    public List<SolutionResponseDTO> getMonthlySolutions(List<IssueResponseDTO> monthlyIssues, int year, int month) {
        List<SolutionResponseDTO> monthlySolutions = new ArrayList<>();

        for (IssueResponseDTO issue : monthlyIssues) {
            List<Solution> solutions = solutionRepository.findByIssueId(issue.getId());

            for (Solution solution : solutions) {
                LocalDateTime createdAt = solution.getCreatedAt();
                if (createdAt.getYear() == year && createdAt.getMonthValue() == month) {
                    monthlySolutions.add(SolutionResponseDTO.toDto(solution));
                }
            }
        }

        return monthlySolutions;
    }

    // 최근 5개월 간의 월별 적금 횟수 계산
    @Transactional(readOnly = true)
    public List<MonthlyCountIssueDTO> getMonthlySolutionsCount(List<IssueResponseDTO> solvedIssues) {
        List<MonthlyCountIssueDTO> monthlyCounts = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();  // 현재 날짜

        for (int i = 0; i < 5; i++) {  // 최근 5개월
            LocalDateTime currentMonth = currentDate.minusMonths(i);
            int count = 0;  // 적금 횟수

            for (IssueResponseDTO issue : solvedIssues) {
                List<Solution> solutions = solutionRepository.findByIssueId(issue.getId());

                for (Solution solution : solutions) {
                    LocalDateTime solutionDate = solution.getCreatedAt().toLocalDate().atStartOfDay();
                    if (solutionDate.getYear() == currentMonth.getYear() && solutionDate.getMonth() == currentMonth.getMonth()) {
                        count++;
                    }
                }
            }

            MonthlyCountIssueDTO monthlyCount = MonthlyCountIssueDTO.builder()
                    .year(currentMonth.getYear())
                    .month(currentMonth.getMonthValue())
                    .count(count)
                    .build();

            monthlyCounts.add(monthlyCount);
        }

        return monthlyCounts;
    }

}
