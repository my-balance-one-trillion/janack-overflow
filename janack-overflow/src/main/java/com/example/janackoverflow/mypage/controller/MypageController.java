package com.example.janackoverflow.mypage.controller;

import com.example.janackoverflow.global.pagination.PageResponseDTO;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.mypage.domain.response.MyCommentResponseDTO;
import com.example.janackoverflow.mypage.domain.response.MyIssueResponseDTO;
import com.example.janackoverflow.user.service.UsersService;
import com.example.janackoverflow.mypage.service.MypageService;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> updateUser(@RequestBody UsersRequestDTO usersRequestDTO,
                                     @AuthenticationPrincipal NowUserDetails nowUserDetails){

        if (!usersRequestDTO.getNickname().equals(nowUserDetails.getUser().getNickname()) //본인의 닉네임과 같으면 검사 제외
        && usersService.isDuplicatedNick(usersRequestDTO)) { //닉네임 중복확인

            System.out.println(!usersRequestDTO.getNickname().equals(nowUserDetails.getUser().getNickname()));
            System.out.println(usersService.isDuplicatedNick(usersRequestDTO));

            return new ResponseEntity<>("중복되는 닉네임입니다", HttpStatus.FORBIDDEN);
        } else {
            Long usersId = nowUserDetails.getId();
            String updateStatus = mypageService.updateUser(usersRequestDTO, usersId);
            System.out.println(updateStatus);
            return ResponseEntity.accepted().body(updateStatus);
        }

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
    public ResponseEntity readMyIssue( @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                       Pageable pageable){
        Long usersId = nowUserDetails.getId();
        PageResponseDTO<MyIssueResponseDTO> myIssueList = mypageService.readMyIssue(usersId, pageable);
        return new ResponseEntity(myIssueList, HttpStatus.OK);
    }

//    내가쓴 댓글 보기
    @GetMapping("/mypage/mycomment")
    public ResponseEntity readMyComment( @AuthenticationPrincipal NowUserDetails nowUserDetails,
                                         Pageable pageable){
        Long usersId = nowUserDetails.getId();
        PageResponseDTO<MyCommentResponseDTO> myCommentList = mypageService.readMyComment(usersId, pageable);
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
