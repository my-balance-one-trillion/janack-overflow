package com.example.janackoverflow.saving.domain.request;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.user.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccountRequestDTO {

    @NotBlank(message="적금명을 입력하세요.")
    private String acntName;

    @NotBlank(message="목표명을 입력하세요.")
    private String goalName;

    @NotBlank(message="목표 금액을 입력하세요.")
    private int goalAmount;


    public InputAccount toEntity(Users users){
        return InputAccount.builder()
                .users(users)
                .acntName(this.acntName)
                .goalName(this.goalName)
                .goalAmount(this.goalAmount)
                .status("01") // 진행중
                .build();
    }
}
