package com.example.janackoverflow.issue.domain.request;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIssueRequestDTO {

    private String title;
    private String content;
    private String code;
    private String category;
    private String keyword;

    public Issue toEntity(Users users){
        return Issue.builder()
                .users(users)
                .title(this.title)
                .content(this.content)
                .code(this.code)
                .category(this.category)
                .keyword(this.keyword)
                .status("01")  // 상태: 01(진행 중)
                .publicStatus(false)  // 공개 : false
                .views(0)  // 조회 수
                .build();
    }
}
