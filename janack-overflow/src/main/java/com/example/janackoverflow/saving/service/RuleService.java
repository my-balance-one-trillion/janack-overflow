package com.example.janackoverflow.saving.service;

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
}
