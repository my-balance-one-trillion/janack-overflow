package com.example.janackoverflow.community.entity;

import com.example.janackoverflow.issue.entity.issue;
import com.example.janackoverflow.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
    private issue issue;
}