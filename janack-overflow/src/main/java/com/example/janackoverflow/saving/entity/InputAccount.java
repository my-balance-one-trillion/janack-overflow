package com.example.janackoverflow.saving.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "input_account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputAccount extends AuditingFields {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acntNum;

    @Column(nullable = false)
    private String acntName;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private int goalAmount;

    private LocalDateTime completedAt;

    @Column(length = 2, nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    private int acntAmount;

    public void updateAcntNum(String newAcntNum) {
        this.acntNum = newAcntNum;
    }

    public void updateAcntAmount(int newAcntAmount) {
        this.acntAmount = newAcntAmount;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void updateCompletedAt() {
        this.completedAt = LocalDateTime.now();
    }

    public void updateAcntName(String newAcntName){ this.acntName = newAcntName; }

    public void updateGoalName(String newGoalName) { this.goalName = newGoalName; }

    public void updateGoalNum(int newGoalAmount) { this.goalAmount = newGoalAmount; }
}
