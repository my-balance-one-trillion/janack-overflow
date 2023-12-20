package com.example.janackoverflow.community.domain.likes;

import com.example.janackoverflow.community.entity.Likes;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikesDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikesRequestDTO {
        private Users users;
        private Issue issue;

        public Likes toEntity() {
            return Likes.builder()
                    .issue(issue)
                    .users(users)
                    .build();
        }
    }
}
