package com.example.janackoverflow.mypage.domain.response;

import com.example.janackoverflow.community.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyCommentResponseDTO {
    private Long id;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    private String issue_title;

    public static MyCommentResponseDTO toDto(Comment comment){
        return MyCommentResponseDTO.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .issue_title(comment.getIssue().getTitle())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
