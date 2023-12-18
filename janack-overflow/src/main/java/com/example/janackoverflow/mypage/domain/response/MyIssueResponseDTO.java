package com.example.janackoverflow.mypage.domain.response;

import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyIssueResponseDTO {
    private Long id;
    private String title;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String status;
    private Integer views;
    private Users users;
    private Boolean publicStatus;

    public static MyIssueResponseDTO toDto(Issue issue){
        return MyIssueResponseDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .category(issue.getCategory())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .views(issue.getViews())
                .users(issue.getUsers())
                .publicStatus(issue.isPublicStatus())
                .build();
    }
}
