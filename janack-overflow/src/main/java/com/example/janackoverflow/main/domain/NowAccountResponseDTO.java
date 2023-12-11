package com.example.janackoverflow.main.domain;

import com.example.janackoverflow.saving.entity.InputAccount;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NowAccountResponseDTO {
    private String acntNum;
    private String acntName;
    private String goalName;
    private int goalAmount;
    private long savingCnt;
    private long dayCnt;
    public static NowAccountResponseDTO toDto(InputAccount inputAccount, long savingCnt){
        return NowAccountResponseDTO.builder()
                .acntNum(inputAccount.getAcntNum())
                .acntName(inputAccount.getAcntName())
                .goalName(inputAccount.getGoalName())
                .goalAmount(inputAccount.getGoalAmount())
                .savingCnt(savingCnt)
                .dayCnt(ChronoUnit.DAYS.between(inputAccount.getCreatedAt(), LocalDateTime.now()))
                .build();


    }
}
