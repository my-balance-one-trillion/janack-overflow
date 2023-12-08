package com.example.janackoverflow.saving.controller;

import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.service.InputAccountService;
import com.example.janackoverflow.saving.service.RuleService;
import com.example.janackoverflow.user.entity.Users;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public SavingController(InputAccountService inputAccountService, RuleService ruleService){
        this.inputAccountService = inputAccountService;
        this.ruleService = ruleService;
    }

    // 적금 개설
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody SavingRequestDTO savingRequestDTO){
        Users users = new Users();
        users.setId(1L);

        try {
            InputAccount inputAccount = inputAccountService.createInputAccount(savingRequestDTO.getInputAccountRequestDTO(), users);
            Rule rule = ruleService.createRule(savingRequestDTO.getRuleRequestDTO(), inputAccount, users);

            Map<String, Object> response = new HashMap<>();
            response.put("inputAccount", inputAccount);
            response.put("rule", rule);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("입력 안됨: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 적금 내역 (진행 중인 적금 정보)
    @GetMapping("/users/{id}/progress")
    public ResponseEntity<?> getUserAccountProgress(@PathVariable("id") long userId){
        Optional<InputAccount> inProgressAccount = inputAccountService.getInProgressAccountByUser(userId);
        return new ResponseEntity<>(inProgressAccount, HttpStatus.OK);
    }

    // 적금 기록 (사용자 적금 전부 조회)
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserAccounts(@PathVariable("id") long id){
        List<InputAccount> userAccounts = inputAccountService.getAccountsByUser(id);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    // 진행 중인 적금 정보 수정
    @PatchMapping("{id}")
    public ResponseEntity<?> updateAccountInfo(@RequestBody SavingRequestDTO savingRequestDTO, @PathVariable("id") long userId){
        InputAccount updateAccount = inputAccountService.updateInputAccount(savingRequestDTO.getInputAccountRequestDTO(), userId);
        Rule updateRule = ruleService.updateRule(savingRequestDTO.getRuleRequestDTO(), updateAccount.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("updateAccount", updateAccount);
        response.put("updateRule", updateRule);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 적금 삭제 (적금 포기 상태로 변경 status=02)
    @PatchMapping("{id}/giveup")
    public ResponseEntity<?> updateAccountStatusGiveup(@PathVariable("id") long userId){
        InputAccount deleteAccount = inputAccountService.deleteInputAccount(userId);
        return new ResponseEntity<>(deleteAccount, HttpStatus.OK);
    }
}
