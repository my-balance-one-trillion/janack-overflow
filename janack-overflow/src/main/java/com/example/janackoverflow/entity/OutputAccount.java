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