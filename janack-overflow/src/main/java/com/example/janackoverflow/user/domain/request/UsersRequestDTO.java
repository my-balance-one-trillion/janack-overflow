package com.example.janackoverflow.user.domain.request;

import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class UsersRequestDTO {
    private Long id;
    private String email;
    private String password;
    private String digit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String name;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String nickname;
    private String role;
    private String status;
    private String profileImage;
    private String holder;
    private String bankName;
    private String outputAcntNum;

    private String newPassword;
    private String newPasswordConfirm;

    public Users toEntity(){
        return Users.of(
                id,
                email,
                password,
                digit,
                birth,
                name,
                createdAt,
                nickname,
                status,
                role,
                profileImage,
                holder,
                bankName,
                outputAcntNum
        );
    }
}