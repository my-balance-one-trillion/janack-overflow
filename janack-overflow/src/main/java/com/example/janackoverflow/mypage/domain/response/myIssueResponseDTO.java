package com.example.janackoverflow.mypage.domain.response;

import com.example.janackoverflow.issue.entity.Issue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class myIssueResponseDTO {
    private String title;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String status;
    private Integer views;

    public static myIssueResponseDTO toDto(Issue issue){
        return myIssueResponseDTO.builder()
                .title(issue.getTitle())
                .category(issue.getCategory())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .views(issue.getViews())
                .build();
    }
}
