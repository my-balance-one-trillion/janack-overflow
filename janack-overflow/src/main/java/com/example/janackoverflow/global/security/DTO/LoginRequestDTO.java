package com.example.janackoverflow.global.security.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}