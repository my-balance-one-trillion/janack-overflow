package com.example.janackoverflow.user.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class UsersResponseDTO {
    private Long id;
    private String email;
    private String password;
    private String digit;
    private Date birth;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    private String nickname;
    private String status;
    private String role;
    private String profileImage;
}
