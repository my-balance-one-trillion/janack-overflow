package com.example.janackoverflow.saving.domain.request;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.user.entity.Users;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleRequestDTO {

    private int underThirty;
    private int underHour;
    private int underThreeHour;
    private int overThreeHour;

    public Rule toEntity(InputAccount inputAccount, Users users) {
        return Rule.builder()
                .users(users)
                .inputAccount(inputAccount)
                .underThirty(this.underThirty)
                .underHour(this.underHour)
                .underThreeHour(this.underThreeHour)
                .overThreeHour(this.overThreeHour)
                .build();
    }
}
