package com.example.janackoverflow.saving.domain.response;

import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.RuleRequestDTO;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SavingResponseDTO {
    private InputAccountResponseDTO inputAccountResponseDTO;
    private RuleResponseDTO ruleResponseDTO;
}
