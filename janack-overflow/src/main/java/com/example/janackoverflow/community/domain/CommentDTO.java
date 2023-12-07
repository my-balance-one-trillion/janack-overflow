package com.example.janackoverflow.community.domain;

import com.example.janackoverflow.community.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.catalina.User;

import java.time.LocalDateTime;

public class CommentDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class CommentRequestDto {
        private String content;

        public Comment toEntity() {
            return Comment.builder()
                    .content(this.content)
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @Builder
    public static class CommentResponseDto {
        private Long id;
        private String comment;
        private LocalDateTime createdAt;
        private Error error;

        public CommentResponseDto toDto() {
            return CommentResponseDto.builder()
                    .id(id)
                    .comment(comment)
                    .build();
        }
    }

}
