package com.example.janackoverflow.issue.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.issue.domain.SolutionDTO;
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

    @Column(nullable = false, length = 1500)
    private String content;

    @Column(nullable = false, length = 1500)
    private String code;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    public SolutionDTO toDto() {
        return SolutionDTO.builder()
                .id(id)
                .content(content)
                .code(code)
                .build();
    }
}