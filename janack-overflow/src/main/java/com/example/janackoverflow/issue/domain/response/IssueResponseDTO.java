package com.example.janackoverflow.issue.domain.response;

import com.example.janackoverflow.issue.entity.Issue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IssueResponseDTO {

    private String title;
    private String keyword;
    private String category;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String status;
    private int amount;

    public static IssueResponseDTO toDto(Issue issue){
        return IssueResponseDTO.builder()
                .title(issue.getTitle())
                .keyword(issue.getKeyword())
                .category(issue.getCategory())
                .code(issue.getCode())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .amount(issue.getAmount())
                .build();
    }
}
