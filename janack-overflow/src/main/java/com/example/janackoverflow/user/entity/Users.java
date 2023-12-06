package com.example.janackoverflow.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String digit;

    private Date birth;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String nickname;

    @Column(length = 2, nullable = false)
    private String status;

    @Column(nullable = false)
    private String role;
}