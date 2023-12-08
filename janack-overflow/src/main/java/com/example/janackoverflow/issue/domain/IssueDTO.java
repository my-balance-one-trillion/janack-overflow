package com.example.janackoverflow.issue.domain;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

public class IssueDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestDTO {
        private int amount;
        private String title;
        private String content;
        private String code;
        private String category;
        private String status;
        private boolean publicStatus;
        // java, spring -> , 로 이어진 문자열
        private String keyword;

        public Issue toEntity(Users users) {
            return Issue.builder()
                    .users(users)
                    .title(title)
                    .content(content)
                    .code(code)
                    .keyword(keyword)
                    .category(category)
                    .amount(amount)
                    .publicStatus(publicStatus)
                    .status(status)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseDTO {
        private int amount;
        private String title;
        private String content;
        private String code;
        private String category;
        private String status; // 해결여부
        private boolean publicStatus; // 공개여부
        private String keyword;
        private Integer views;

        public IssueDTO.ResponseDTO toDto() {
            return ResponseDTO.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .code(code)
                    .keyword(keyword)
//                    .status(status)
//                    .publicStatus(publicStatus)
                    .amount(amount)
                    .views(views)
                    .build();
        }
    }
}
