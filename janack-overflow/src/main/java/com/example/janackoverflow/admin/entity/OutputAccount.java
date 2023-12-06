package com.example.janackoverflow.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "output_account")
public class OutputAccount  {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String acntNum;

    @Column(nullable = false)
    private String holder;

    @Column(nullable = false)
    private String bankCode;
}