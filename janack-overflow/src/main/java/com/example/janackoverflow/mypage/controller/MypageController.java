package com.example.janackoverflow.mypage.controller;

import com.example.janackoverflow.community.domain.CommentDTO;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.user.service.UsersService;
import com.example.janackoverflow.mypage.service.MypageService;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //    마이페이지에서 회원정보 수정
    @GetMapping("/mypage/myinfo")
    public ResponseEntity readUser(@AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        System.out.println(usersId);
        UsersResponseDTO usersResponseDTO =  usersService.readUser(usersId);
        return new ResponseEntity(usersResponseDTO, HttpStatus.OK);
    }
    @PutMapping("/mypage/myinfo")
    public ResponseEntity updateUser(@RequestBody UsersRequestDTO usersRequestDTO,
                                     @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        mypageService.updateUser(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

    //    마이페이지에서 프로필 이미지 변경
    @PutMapping("/mypage/profileimage")
    public ResponseEntity updateProfileImage(@RequestBody UsersRequestDTO usersRequestDTO,
                                             @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        mypageService.updateProfileImage(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

//    내가 쓴 글 보기
    @GetMapping("mypage/myissue")
    public ResponseEntity readMyIssue( @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        Page<IssueResponseDTO> myIssueList = mypageService.readMyIssue(usersId);
        return new ResponseEntity(myIssueList, HttpStatus.OK);
    }
//    내가쓴 댓글 보기
    @GetMapping("/mypage/mycomment")
    public ResponseEntity readMyComment( @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        Page<CommentDTO.ResponseDto> myCommentList = mypageService.readMyComment(usersId);
        return new ResponseEntity(myCommentList ,HttpStatus.OK);
    }

//    내가 쓴 댓글 지우기
    @DeleteMapping("/mypage/mycomment/{commentid}")
    public ResponseEntity deleteMyComment(@PathVariable("commentid") Long commentId,
                                          @AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getId();
        mypageService.deleteMyComment(commentId, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
}
