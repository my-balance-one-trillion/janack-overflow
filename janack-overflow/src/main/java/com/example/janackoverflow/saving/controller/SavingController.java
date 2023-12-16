package com.example.janackoverflow.saving.controller;

import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.service.IssueService;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.domain.response.InputAccountResponseDTO;
import com.example.janackoverflow.saving.domain.response.RuleResponseDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.service.InputAccountService;
import com.example.janackoverflow.saving.service.RuleService;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/savings")
public class SavingController {

    private final InputAccountService inputAccountService;
    private final RuleService ruleService;
    private final IssueService issueService;

    public SavingController(InputAccountService inputAccountService, RuleService ruleService, IssueService issueService){
        this.inputAccountService = inputAccountService;
        this.ruleService = ruleService;
        this.issueService = issueService;
    }

    // 적금 개설
    @PostMapping
    public ResponseEntity<?> createAccount(@Validated @RequestBody SavingRequestDTO savingRequestDTO, @AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
        try {
            InputAccount inputAccount = inputAccountService.createInputAccount(savingRequestDTO.getInputAccountRequestDTO(), users);
            if (inputAccount != null) {
                Rule rule = ruleService.createRule(savingRequestDTO.getRuleRequestDTO(), inputAccount, users);

                Map<String, Object> response = new HashMap<>();
                response.put("inputAccount", inputAccount);
                response.put("rule", rule);

                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("적금 개설에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("입력 안됨: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 적금 내역 (진행 중인 적금 정보)
    @GetMapping("/progress")
    public ResponseEntity<?> getUserAccountInProgress(@AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
        Optional<InputAccountResponseDTO> inProgressAccount = inputAccountService.getInProgressAccountByUser(users);
        Optional<RuleResponseDTO> inProgressRule = ruleService.getInProgressRuleByUser(users, inProgressAccount);

        Map<String, Object> response = new HashMap<>();
        response.put("inProgressAccount", inProgressAccount);
        response.put("inProgressRule", inProgressRule);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 월별 내역 조회 (해결된 에러 내역 월별 조회)
    @GetMapping("/monthly-issues")
    public ResponseEntity<?> getMonthlyIssuesByUserAndDate(@AuthenticationPrincipal NowUserDetails userDetails,
                                                           @RequestParam int year, @RequestParam int month) {
        Users users = userDetails.getUser();

        List<Issue> monthlyIssues = issueService.getMonthlyIssuesByUserId(users, year, month);
        return new ResponseEntity<>(monthlyIssues, HttpStatus.OK);
    }


    // 적금 기록 (사용자 적금 전부 조회)
    @GetMapping
    public ResponseEntity<?> getUserAccounts(@AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
        List<InputAccountResponseDTO> userAccounts = inputAccountService.getAccountsByUser(users);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    // 진행 중인 적금 정보 수정
    @PutMapping
    public ResponseEntity<?> updateAccountInfo(@RequestBody SavingRequestDTO savingRequestDTO, @AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
        InputAccount updateAccount = inputAccountService.updateInputAccount(savingRequestDTO.getInputAccountRequestDTO(), users);
        Rule updateRule = ruleService.updateRule(savingRequestDTO.getRuleRequestDTO(),users, updateAccount.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("updateAccount", updateAccount);
        response.put("updateRule", updateRule);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 적금 삭제 (적금 포기 상태로 변경 status=02)
    @PutMapping("/giveup")
    public ResponseEntity<?> updateAccountStatusGiveup(@AuthenticationPrincipal NowUserDetails userDetails){
        Long userId = userDetails.getUser().getId();
        InputAccount deleteAccount = inputAccountService.deleteInputAccount(userId);
        return new ResponseEntity<>(deleteAccount, HttpStatus.OK);
    }
}
