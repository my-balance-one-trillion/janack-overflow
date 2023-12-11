package com.example.janackoverflow.issue.domain.request;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIssueRequestDTO {

    @NotBlank(message = "에러 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "에러 내용을 입력해주세요.")
    private String content;
    @NotBlank(message = "에러 코드를 입력해주세요.")
    private String code;
    @NotBlank(message = "에러 카테고리를 선택해주세요.")
    private String category;
    private String keyword;

    public Issue toEntity(Users users){
        return Issue.builder()
                .users(users)
                .title(title)
                .content(content)
                .code(code)
                .category(category)
                .keyword(keyword)
                .status("01")  // 상태: 01(진행 중)
                .publicStatus(false)  // 공개 : false
                .views(0)  // 조회 수
                .build();
    }
}
