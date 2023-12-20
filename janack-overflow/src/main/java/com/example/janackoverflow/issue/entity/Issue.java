package com.example.janackoverflow.issue.entity;
import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.community.domain.MediumArticle;
import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.domain.SolutionDTO;
import com.example.janackoverflow.issue.domain.request.CreateIssueRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "issue")
@NoArgsConstructor
@AllArgsConstructor
public class Issue extends AuditingFields {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ColumnDefault(value = "0")
    private int amount;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false, length = 10000)
    private String code;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @ColumnDefault(value = "'01'")
    private String status; // 해결여부 01: 진행중, 02: 포기, 03: 해결

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean publicStatus; // false : 비공개, true : 공개

    private String keyword; // comma (,) 로 append

    @ColumnDefault(value = "0")
    private Integer views;

    @ManyToOne
    @JoinColumn(name="inputAccount_id")
    private InputAccount inputAccount;

    public IssueDTO.ResponseDTO toDto(long likes, CommunityUsersDTO communityUsersDTO) {
        return IssueDTO.ResponseDTO.builder()
                .id(id)
                .communityUsersDTO(communityUsersDTO)
                .title(title)
                .content(content)
                .category(category)
                .code(code)
                .keyword(keyword)
                .createdAt(getCreatedAt())
                .status(status)
                .publicStatus(publicStatus)
                .amount(amount)
                .likes(likes)
                .views(views)
                .build();
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void updateAmount(int newAmount) {
        this.amount = newAmount;
    }

    public void updatePublicStatus(Boolean newPublicStatus){
        this.publicStatus = newPublicStatus;
    }

    public IssueDTO.ResponseDTO toDetailDto(long likes, CommunityUsersDTO communityUsersDTO, List<CommentDTO.ResponseDto> commentResponseDtoList, SolutionDTO solutionDTO) {
        return IssueDTO.ResponseDTO.builder()
                .id(id)
                .communityUsersDTO(communityUsersDTO)
                .commentResponseDtoList(commentResponseDtoList)
                .title(title)
                .content(content)
                .category(category)
                .code(code)
                .keyword(keyword)
                .status(status)
                .publicStatus(publicStatus)
                .likes(likes)
                .views(views)
                .createdAt(getCreatedAt())
                .solutionDTO(solutionDTO)
//                .articleList(articleList)
                .build();
    }

    public void updateViews() {
        views += 1;
    }
}