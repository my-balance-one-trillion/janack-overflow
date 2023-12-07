package com.example.janackoverflow.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "issue")
public class issue {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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
}