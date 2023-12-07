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
    private String digit;
    private Date birth;
    private String name;
    private LocalDateTime createdAt;
    private String nickname;
    private String status;
    private String role;
    private String profileImage;
    private String holder;
    private String bankName;
    private String outputAcntNum;

    public static UsersResponseDTO from(Long id, String email, String digit,
                                        Date birth, String name, LocalDateTime createdAt,
                                        String nickname, String status, String role, String profileImage, String holder,
                                        String bankName, String outputAcntNum){
        return new UsersResponseDTO(id,email,digit,
                birth,name,createdAt,
                nickname,status,role,profileImage,holder,
                bankName,outputAcntNum);
    }
}
