package com.example.janackoverflow.issue.domain.request;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSolutionRequestDTO{
    @NotBlank(message = "해결 코드를 입력해주세요.")
    private String code;
    @NotBlank(message = "해결 내용을 입력해주세요.")
    private String content;
    private Boolean publicStatus;

    public Solution toEntity(Issue issue){
        return Solution.builder()
                .code(code)
                .content(content)
                .issue(issue)
                .build();
    }
}