package com.example.janackoverflow.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "solution")
public class Solution {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "error_id")
    private Error error;
}