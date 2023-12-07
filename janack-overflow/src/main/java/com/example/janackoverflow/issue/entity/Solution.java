package com.example.janackoverflow.issue.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table(name = "solution")
@NoArgsConstructor
@AllArgsConstructor
public class Solution extends AuditingFields {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
}