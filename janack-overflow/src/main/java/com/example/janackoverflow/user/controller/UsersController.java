package com.example.janackoverflow.user.controller;

import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

//    회원가입
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UsersRequestDTO usersRequestDTO){
        usersService.createUser(usersRequestDTO);
        return new ResponseEntity<>(usersRequestDTO, HttpStatus.CREATED);
    }
//    이메일 인증

//    닉네임 중복확인

//    자체 로그인 기능

//    아이디 찾기

//    비밀번호 재설정

//    비밀번호 찾기

//    마이페이지에서 비밀번호 재설정
    @GetMapping
    public ResponseEntity readUser(long userId){
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/mypage")
    public ResponseEntity updateUser(@RequestBody UsersRequestDTO usersRequestDTO, long userId){
        usersService.updateUser(usersRequestDTO, userId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
//    마이페이지에서 프로필 이미지 변경


}
