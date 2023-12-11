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

    private String title;
    private String keyword;
    private String category;
    private String code;
    private LocalDateTime createdAt;

    public static IssueResponseDTO toDto(Issue issue){
        return IssueResponseDTO.builder()
                .title(issue.getTitle())
                .keyword(issue.getKeyword())
                .category(issue.getCategory())
                .code(issue.getCode())
                .createdAt(issue.getCreatedAt())
                .build();
    }
}
