package com.example.janackoverflow.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "input_account_id")
    private InputAccount inputAccount;
}