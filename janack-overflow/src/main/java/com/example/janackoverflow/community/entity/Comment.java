package com.example.janackoverflow.community.entity;

import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.global.entity.AuditingFields;
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

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="issue_id")
    private Issue issue;
}