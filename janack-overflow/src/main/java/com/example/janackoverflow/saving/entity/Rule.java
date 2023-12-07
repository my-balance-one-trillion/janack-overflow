package com.example.janackoverflow.saving.entity;

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
    private int underThirty;
    @Column(nullable = false)
    private int underHour;
    @Column(nullable = false)
    private int underThreeHour;
    @Column(nullable = false)
    private int overThreeHour;

    @ManyToOne
    @JoinColumn(name = "input_account_id")
    private InputAccount inputAccount;
}