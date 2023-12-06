package com.example.janackoverflow.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "input_account")
public class InputAccount {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String acntNum;

    @Column(nullable = false)
    private String acntName;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private int goalAmount;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    @Column(length = 2, nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "output_account_id")
    private OutputAccount outputAccount;
}