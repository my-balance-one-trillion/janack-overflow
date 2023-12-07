package com.example.janackoverflow.community.domain.response;

import com.example.janackoverflow.community.entity.Comment;

public class CommentResponseDTO {
    private Long id;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}
