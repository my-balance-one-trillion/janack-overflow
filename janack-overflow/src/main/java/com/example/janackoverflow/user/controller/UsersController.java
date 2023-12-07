package com.example.janackoverflow.user.controller;

import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
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

        if (usersService.isDuplicatedNick(usersRequestDTO)) { //닉네임 중복확인
            return new ResponseEntity<>("중복되는 닉네임입니다", HttpStatus.FORBIDDEN);
        } else if(usersService.isDuplicatedEmail(usersRequestDTO)) { //이메일 중복확인
            return new ResponseEntity<>("중복되는 이메일입니다", HttpStatus.FORBIDDEN);
        } else {
            usersRequestDTO.setRole("user"); //역활
            usersRequestDTO.setStatus("1"); //상태
            usersRequestDTO.setProfileImage("default.png"); //프로필 이미지
            usersService.createUser(usersRequestDTO);
            //return new ResponseEntity<>(usersRequestDTO, HttpStatus.CREATED);
            return new ResponseEntity<>("성공적으로 생성", HttpStatus.OK);
        }

    }
//    이메일 인증

//    자체 로그인 기능

//    아이디 찾기

//    비밀번호 재설정

//    비밀번호 찾기

//    마이페이지에서 비밀번호 재설정
    @GetMapping("/mypage/{usersid}")
    public ResponseEntity readUser(@PathVariable("usersid") long usersId){
        UsersResponseDTO usersResponseDTO =  usersService.readUser(usersId);
        return new ResponseEntity(usersResponseDTO, HttpStatus.OK);
    }
    @PutMapping("/mypage/{usersid}")
    public ResponseEntity updateUser(@RequestBody UsersRequestDTO usersRequestDTO,
                                     @PathVariable("usersid") long usersId){
        usersService.updateUser(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
//    마이페이지에서 프로필 이미지 변경
    @PutMapping("/mypage/profileimage/{usersid}")
    public ResponseEntity updateProfileImage(@RequestBody UsersRequestDTO usersRequestDTO,
                                             @PathVariable("usersid") long userId){
        usersService.updateProfileImage(usersRequestDTO, userId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

}
