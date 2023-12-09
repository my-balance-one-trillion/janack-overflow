package com.example.janackoverflow.issue.domain;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SolutionDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDTO{
        private Long id;
        private String code;
        private String content;
        private Long issueId;
        private Boolean publicStatus;

        public Solution toEntity(Issue issue){
            return Solution.builder()
                    .id(id)
                    .code(code)
                    .content(content)
                    .issue(issue)
                    .build();
        }
    }
}
