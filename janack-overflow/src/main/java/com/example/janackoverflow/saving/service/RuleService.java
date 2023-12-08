package com.example.janackoverflow.saving.service;

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
        return ruleRepository.save(ruleRequestDTO.toEntity(inputAccount, users));
    }

    // 규칙 수정
    @Transactional
    public Rule updateRule(RuleRequestDTO ruleRequestDTO, long accountId){
        Rule updateRule = ruleRepository.findByInputAccountId(accountId);

        updateRule.setUnderThirty(ruleRequestDTO.getUnderThirty());
        updateRule.setUnderHour(ruleRequestDTO.getUnderHour());
        updateRule.setUnderThreeHour(ruleRequestDTO.getUnderThreeHour());
        updateRule.setOverThreeHour(ruleRequestDTO.getOverThreeHour());

        return ruleRepository.save(updateRule);
    }
}
