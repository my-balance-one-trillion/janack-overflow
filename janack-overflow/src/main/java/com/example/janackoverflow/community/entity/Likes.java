package com.example.janackoverflow.community.entity;

import com.example.janackoverflow.community.domain.likes.LikesId;
import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@IdClass(LikesId.class)
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Likes extends AuditingFields {
    @ManyToOne
    @Id
    @JoinColumn(name="users_id", nullable = false)
    private Users users;

    @ManyToOne
    @Id
    @JoinColumn(name="issue_id", nullable = false)
    private Issue issue;
}