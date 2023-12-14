package com.example.janackoverflow.user.controller;

import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

//    회원가입
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UsersRequestDTO usersRequestDTO){

        if(usersRequestDTO.getEmail().isEmpty()
        || usersRequestDTO.getPassword().isEmpty()
        || usersRequestDTO.getName().isEmpty()
        || usersRequestDTO.getNickname().isEmpty()
        || usersRequestDTO.getDigit().isEmpty()
        || usersRequestDTO.getBirth() == null){
            return new ResponseEntity<>("필수 입력 항목입니다", HttpStatus.FORBIDDEN);
        }

        if (usersService.isDuplicatedNick(usersRequestDTO)) { //닉네임 중복확인
            return new ResponseEntity<>("중복되는 닉네임입니다", HttpStatus.FORBIDDEN);
        } else if(usersService.isDuplicatedEmail(usersRequestDTO)) { //이메일 중복확인
            return new ResponseEntity<>("중복되는 이메일입니다", HttpStatus.FORBIDDEN);
        } else {
            usersRequestDTO.setRole("USER"); //역활
            usersRequestDTO.setStatus("01"); //상태
            usersRequestDTO.setProfileImage("default.png"); //프로필 이미지

            usersService.createUser(usersRequestDTO);

            return new ResponseEntity<>("성공적으로 생성", HttpStatus.OK);
        }

    }

    @GetMapping("/userLogout")
    public ResponseEntity logout(){

        System.out.println("로그아웃 수행");

        SecurityContextHolder.clearContext();

        return new ResponseEntity("로그아웃을 수행했습니다", HttpStatus.OK);
    }

//    이메일 인증

//    아이디 찾기

//    비밀번호 재설정

//    비밀번호 찾기
}
