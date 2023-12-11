package com.example.janackoverflow.saving.entity;

import com.example.janackoverflow.global.entity.AuditingFields;
import com.example.janackoverflow.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "rule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule{
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

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    public void updateRule(int underThirty, int underHour, int underThreeHour, int overThreeHour){
        this.underThirty = underThirty;
        this.underHour = underHour;
        this.underThreeHour = underThreeHour;
        this.overThreeHour = overThreeHour;
    }
}
