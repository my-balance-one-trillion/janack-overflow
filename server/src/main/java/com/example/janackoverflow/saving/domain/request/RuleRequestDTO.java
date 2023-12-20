package com.example.janackoverflow.saving.domain.request;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.user.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleRequestDTO {

    @NotBlank(message="금액을 입력하세요.")
    private int underThirty;
    @NotBlank(message="금액을 입력하세요.")
    private int underHour;
    @NotBlank(message="금액을 입력하세요.")
    private int underThreeHour;
    @NotBlank(message="금액을 입력하세요.")
    private int overThreeHour;

    public Rule toEntity(InputAccount inputAccount, Users users) {
        return Rule.builder()
                .users(users)
                .inputAccount(inputAccount)
                .underThirty(underThirty)
                .underHour(underHour)
                .underThreeHour(underThreeHour)
                .overThreeHour(overThreeHour)
                .build();
    }
}
