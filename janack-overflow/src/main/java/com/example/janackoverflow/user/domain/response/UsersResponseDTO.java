package com.example.janackoverflow.user.domain.response;

import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@Builder
public class UsersResponseDTO {
    private Long id;
    private String email;
    private String digit;
    private Date birth;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String nickname;
    private String status;
    private String role;
    private String profileImage;
    private String holder;
    private String bankName;
    private String outputAcntNum;
}
