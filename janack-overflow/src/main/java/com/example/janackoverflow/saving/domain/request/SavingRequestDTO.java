package com.example.janackoverflow.saving.domain.request;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.user.entity.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SavingRequestDTO {
    private  InputAccountRequestDTO inputAccountRequestDTO;
    private  RuleRequestDTO ruleRequestDTO;
}
