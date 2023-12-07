package com.example.janackoverflow.issue.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    private int amount;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean publicStatus;

    private String keyword;

    @ColumnDefault("0")
    private Integer views;
}