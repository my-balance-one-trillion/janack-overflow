package com.example.janackoverflow.issue.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.user.entity.Users;
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

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

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
    @ColumnDefault(value = "01")
    private String status; // 해결여부 01: 진행중, 02: 포기, 03: 해결

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean publicStatus; // false : 비공개, true : 공개

    private String keyword; // comma (,) 로 append

    @ColumnDefault("0")
    private Integer views;
}