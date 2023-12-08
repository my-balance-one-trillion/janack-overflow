package com.example.janackoverflow.saving.domain.response;

import com.example.janackoverflow.saving.entity.InputAccount;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccountResponseDTO {

    private  String acntName;
    private String goalName;
    private int goalNum;

    public static InputAccountResponseDTO toDto(InputAccount inputAccount){
        return InputAccountResponseDTO.builder()
                .acntName(inputAccount.getAcntName())
                .goalName(inputAccount.getGoalName())
                .goalNum(inputAccount.getGoalAmount())
                .build();
    }
}
