package com.example.janackoverflow.issue.domain;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.community.domain.MediumArticle;
import com.example.janackoverflow.community.entity.Comment;
import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

public class IssueDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestDTO {
        private long id;
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
                    .id(id)
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
        private long id;
        private int amount;
        private String title;
        private String content;
        private String code;
        private String category;
        private String status; // 해결여부
        private boolean publicStatus; // 공개여부
        private String keyword;
        private Integer views;
        private long likes;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime createdAt;
        private SolutionDTO solutionDTO;
        private CommunityUsersDTO communityUsersDTO;
        private List<CommentDTO.ResponseDto> commentResponseDtoList;
        private List<MediumArticle> articleList;
    }
}
