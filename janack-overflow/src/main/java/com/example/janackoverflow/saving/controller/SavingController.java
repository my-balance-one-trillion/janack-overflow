package com.example.janackoverflow.saving.controller;

import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.RuleRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.service.InputAccountService;
import com.example.janackoverflow.saving.service.RuleService;
import com.example.janackoverflow.user.entity.Users;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity createAccount(@RequestBody SavingRequestDTO savingRequestDTO){
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

    // 적금 기록 조회
    @GetMapping
    public ResponseEntity getUserAccounts(@PathVariable("id") long id){
        // Users user = userService.getUserById(userId);
        List<InputAccount> userAccounts = inputAccountService.getAccountsByUser(id);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    // 현재 적금 수정

}
