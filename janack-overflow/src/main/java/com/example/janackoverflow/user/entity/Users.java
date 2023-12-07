package com.example.janackoverflow.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
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

    @Column(nullable = false)
    private String profileImage;

    public static Users of(Long id,String email,
                           String password,String digit,
                           Date birth,String name,LocalDateTime createdAt,
                           String nickname,String status, String role,String profileImage
                           ){
        return new Users(null,email,
                password,digit,
                birth,name,createdAt,
                nickname,status,role,profileImage);
    }

    public void updatePassword(String password){
        this.password = password;
    }
    public void updateDigit(String digit){
        this.digit = digit;
    }
    public void updateBirth(Date birth){
        this.birth = birth;
    }
    public void updateName(String name){
        this.name = name;
    }
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}