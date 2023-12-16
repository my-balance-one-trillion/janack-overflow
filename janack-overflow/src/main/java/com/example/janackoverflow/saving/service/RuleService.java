package com.example.janackoverflow.saving.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.saving.domain.request.RuleRequestDTO;
import com.example.janackoverflow.saving.domain.response.InputAccountResponseDTO;
import com.example.janackoverflow.saving.domain.response.RuleResponseDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.RuleRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    // 규칙 생성
    @Transactional
    public Rule createRule(RuleRequestDTO ruleRequestDTO, InputAccount inputAccount, Users users) {
        if(ruleRepository.findByInputAccountIdAndUsersId(inputAccount.getId(), users.getId()).isPresent()){
            throw new BusinessLogicException(ExceptionCode.RULE_NOT_FOUND);
        }
        return ruleRepository.save(ruleRequestDTO.toEntity(inputAccount, users));
    }

    // 규칙 수정
    @Transactional
    public Rule updateRule(RuleRequestDTO ruleRequestDTO, Users users, Long accountId){
        Rule updateRule = ruleRepository.findByInputAccountIdAndUsersId(accountId, users.getId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RULE_NOT_FOUND));
        updateRule.updateRule(ruleRequestDTO.getUnderThirty(),
                ruleRequestDTO.getUnderHour(),
                ruleRequestDTO.getUnderThreeHour(),
                ruleRequestDTO.getOverThreeHour());
        return ruleRepository.save(updateRule);
    }

    // 규칙 조회
    @Transactional(readOnly = true)
    public Optional<RuleResponseDTO> getInProgressRuleByUser(Users users, Optional<InputAccountResponseDTO> inProgressAccount) {
        InputAccountResponseDTO account = inProgressAccount.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RULE_NOT_FOUND));
        return ruleRepository.findByUsersIdAndInputAccountId(users.getId(), account.getId())
                .map(RuleResponseDTO::toDto);
    }
}
