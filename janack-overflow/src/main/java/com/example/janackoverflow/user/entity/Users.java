package com.example.janackoverflow.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String nickname;

    @Column(length = 2, nullable = false )
    private String status;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String profileImage;

//    @Column(nullable = false)
    private String holder;

//    @Column(nullable = false)
    private String bankName;

//    @Column(nullable = false)
    private String outputAcntNum;

    public static Users of(Long id,String email,
                           String password,String digit,
                           Date birth,String name,LocalDateTime createdAt,
                           String nickname,String status, String role,String profileImage
                           ,String holder, String bankName, String outputAcntNum
                           ){
        return new Users(null,email,
                password,digit,
                birth,name,createdAt,
                nickname,status,role,profileImage,
                holder,bankName,outputAcntNum);
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
    // 입금주 관련 업뎃 메서드
    public void updateHolder(String holder){
        this.holder = holder;
    }
    public void updateBankName(String bankName){
        this.bankName = bankName;
    }
    public void updateOutputAcntNum(String outputAcntNum){
        this.outputAcntNum = outputAcntNum;
    }

}