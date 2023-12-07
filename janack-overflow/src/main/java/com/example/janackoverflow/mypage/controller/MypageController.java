package com.example.janackoverflow.mypage.controller;

import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MypageController {
    private final UsersService usersService;

    public MypageController(UsersService usersService){
        this.usersService = usersService;
    }

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
