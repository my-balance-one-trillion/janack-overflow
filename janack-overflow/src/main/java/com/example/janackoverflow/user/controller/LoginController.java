package com.example.janackoverflow.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity loginTest(){
        return new ResponseEntity<>("Not Security Process", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity adminTest(){
        return new ResponseEntity<>("관리자만 열람 가능한 정보입니다", HttpStatus.OK);
    }

}
