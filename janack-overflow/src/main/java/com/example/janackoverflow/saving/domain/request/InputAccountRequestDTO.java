package com.example.janackoverflow.saving.domain.request;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.user.entity.Users;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccountRequestDTO {

    private String acntName;
    private String goalName;
    private int goalAmount;
    private String status;

    public InputAccount toEntity(Users users){
        return InputAccount.builder()
                .users(users)
                .acntName(this.acntName)
                .goalName(this.goalName)
                .goalAmount(this.goalAmount)
                .status("0") // 진행중
                .build();
    }
}
