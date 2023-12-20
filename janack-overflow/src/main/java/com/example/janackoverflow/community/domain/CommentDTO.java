package com.example.janackoverflow.community.domain;

import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CommentDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentRequestDto {
        private Issue issue;
        private Users users;
        private String content;

        public Comment toEntity(Issue issue, Users users) {
            return Comment.builder()
                    .content(content)
                    .issue(issue)
                    .users(users)
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @Builder
    public static class ResponseDto {
        private Long id;
        private String comment;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
        private Long issueId;
//        private CommunityUsersDTO communityUsersDTO;
        private String profileImage;
        private String nickname;
    }

}
