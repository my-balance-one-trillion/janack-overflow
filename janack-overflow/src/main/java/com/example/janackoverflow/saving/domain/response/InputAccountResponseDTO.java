package com.example.janackoverflow.saving.domain.response;

import com.example.janackoverflow.saving.entity.InputAccount;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccountResponseDTO {

    private Long id;
    private  String acntName;
    private String acntNum;
    private int acntAmount;
    private String goalName;
    private int goalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String status;

    public static InputAccountResponseDTO toDto(InputAccount inputAccount){
        return InputAccountResponseDTO.builder()
                .id(inputAccount.getId())
                .acntName(inputAccount.getAcntName())
                .acntNum(inputAccount.getAcntNum())
                .acntAmount(inputAccount.getAcntAmount())
                .goalName(inputAccount.getGoalName())
                .goalAmount(inputAccount.getGoalAmount())
                .createdAt(inputAccount.getCreatedAt())
                .completedAt(inputAccount.getCompletedAt())
                .status(inputAccount.getStatus())
                .build();
    }
}
