package com.example.janackoverflow.saving.domain.response;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAmountDTO {
    private int year;
    private int month;
    private int amount;
}
