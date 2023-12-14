package com.example.janackoverflow.issue.domain.response;

import com.example.janackoverflow.issue.entity.Issue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseDTO {

    private Long id;
    private String title;
    private String keyword;
    private String category;
    private String code;
    private LocalDateTime IssueCreatedAt;
    private int amount;

    public static IssueResponseDTO toDto(Issue issue){
        return IssueResponseDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .keyword(issue.getKeyword())
                .category(issue.getCategory())
                .code(issue.getCode())
                .IssueCreatedAt(issue.getCreatedAt())
                .amount(issue.getAmount())
                .build();
    }
}
