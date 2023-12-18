package com.example.janackoverflow.saving.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MonthlyCountIssueDTO {
    private int year;
    private int month;
    private int count;
}
