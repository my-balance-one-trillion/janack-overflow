package com.example.janackoverflow.user.entity;

import com.example.janackoverflow.community.domain.CommunityUsersDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
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
    @Setter
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

    private String holder;
    private String bankName;
    private String outputAcntNum;

    public static Users of(Long id,String email,
                           String password,String digit,
                           Date birth,String name,LocalDateTime createdAt,
                           String nickname,String status, String role,String profileImage
            ,String holder, String bankName, String outputAcntNum
    ){
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .digit(digit)
                .birth(birth)
                .name(name)
                .createdAt(createdAt)
                .nickname(nickname)
                .status(status)
                .role(role)
                .profileImage(profileImage)
                .holder(holder)
                .bankName(bankName)
                .outputAcntNum(outputAcntNum)
                .build();
    }

    public CommunityUsersDTO toIssueDto() {
        return CommunityUsersDTO.builder()
                .id(id)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }

    public void updatePassword(String password){
        this.password = password;
    }


}