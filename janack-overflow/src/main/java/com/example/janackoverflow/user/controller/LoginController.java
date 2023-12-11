package com.example.janackoverflow.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity login(){
        return new ResponseEntity<>("로그인이 수행됐습니다 (시큐리티 X)", HttpStatus.OK);
    }

}
