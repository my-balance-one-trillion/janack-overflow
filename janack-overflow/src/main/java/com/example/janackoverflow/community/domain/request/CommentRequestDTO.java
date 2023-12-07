package com.example.janackoverflow.community.domain.request;


import com.example.janackoverflow.community.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {
    private Long id;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}
