package com.example.janackoverflow.community.entity;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends AuditingFields {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="issue_id")
    private Issue issue;

    public CommentDTO.ResponseDto toDto() {
        return CommentDTO.ResponseDto.builder()
                .id(id)
                .comment(content)
                .createdAt(getCreatedAt())
                .nickname(users.getNickname())
                .profileImage(users.getProfileImage())
                .issueId(issue.getId())
//                .communityUsersDTO(users.toIssueDto())
                .build();
    }
}