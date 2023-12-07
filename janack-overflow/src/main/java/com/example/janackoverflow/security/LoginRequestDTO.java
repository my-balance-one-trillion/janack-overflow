package com.example.janackoverflow.security;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
