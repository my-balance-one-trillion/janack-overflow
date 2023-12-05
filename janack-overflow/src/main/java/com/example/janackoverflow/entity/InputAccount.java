package com.example.janackoverflow.entity;

import com.example.janackoverflow.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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