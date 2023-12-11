package com.example.janackoverflow.mypage.controller;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.user.service.UsersService;
import com.example.janackoverflow.mypage.service.MypageService;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class MypageController {
    private final MypageService mypageService;
    private final UsersService usersService;

    public MypageController(MypageService mypageService, UsersService usersService){
        this.mypageService = mypageService;
        this.usersService = usersService;
    }

    //    마이페이지에서 비밀번호 재설정
    @GetMapping("/mypage/{usersid}")
    public ResponseEntity readUser(@PathVariable("usersid") Long usersId){
        UsersResponseDTO usersResponseDTO =  usersService.readUser(usersId);
        return new ResponseEntity(usersResponseDTO, HttpStatus.OK);
    }
    @PutMapping("/mypage/{usersid}")
    public ResponseEntity updateUser(@RequestBody UsersRequestDTO usersRequestDTO,
                                     @PathVariable("usersid") Long usersId){
        mypageService.updateUser(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

    //    마이페이지에서 프로필 이미지 변경
    @PutMapping("/mypage/profileimage/{usersid}")
    public ResponseEntity updateProfileImage(@RequestBody UsersRequestDTO usersRequestDTO,
                                             @PathVariable("usersid") Long usersId){
        mypageService.updateProfileImage(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

//    내가 쓴 글 보기

//    내가쓴 댓글 보기
    @GetMapping("/mypage/mycomment/{usersid}")
    public ResponseEntity readMyComment(@PathVariable("usersid") Long usersId){
        Page<CommentDTO.ResponseDto> myCommentList = mypageService.readMyComment(usersId);
        return new ResponseEntity(myCommentList ,HttpStatus.OK);
    }

//    내가 쓴 댓글 지우기
    @DeleteMapping("/mypage/mycomment/{usersId}/{commentid}")
    public ResponseEntity deleteMyComment(@PathVariable("usersId") Long usersId,
                                          @PathVariable("commentid") Long commentId){
        mypageService.deleteMyComment(commentId, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
}
