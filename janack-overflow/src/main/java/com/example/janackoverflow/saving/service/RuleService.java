package com.example.janackoverflow.saving.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.RuleRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.RuleRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Rule updateRule(RuleRequestDTO ruleRequestDTO, Long userId, Long accountId){
        Rule updateRule = ruleRepository.findByInputAccountIdAndUsersId(accountId, userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RULE_NOT_FOUND));
        updateRule.updateRule(ruleRequestDTO.getUnderThirty(),
                ruleRequestDTO.getUnderHour(),
                ruleRequestDTO.getUnderThreeHour(),
                ruleRequestDTO.getUnderThreeHour());
        return ruleRepository.save(updateRule);
    }
}
