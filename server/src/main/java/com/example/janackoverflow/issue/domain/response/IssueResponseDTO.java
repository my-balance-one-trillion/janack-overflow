package com.example.janackoverflow.issue.domain.response;

import com.example.janackoverflow.issue.entity.Issue;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IssueResponseDTO {

    private Long id;
    private String title;
    private String keyword;
    private String category;
    private String code;
    private String content;
    private LocalDateTime createdAt;
    private String status;
    private int amount;

    public static IssueResponseDTO toDto(Issue issue){
        return IssueResponseDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .keyword(issue.getKeyword())
                .category(issue.getCategory())
                .code(issue.getCode())
                .content(issue.getContent())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .amount(issue.getAmount())
                .build();
    }
}
