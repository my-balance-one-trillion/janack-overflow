package com.example.janackoverflow.admin.controller;

import com.example.janackoverflow.admin.service.AdminService;
import com.example.janackoverflow.global.pagination.PageResponseDTO;
import com.example.janackoverflow.mypage.domain.response.MyIssueResponseDTO;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    //전체 유저 보기
    @GetMapping("/admin/users")
    public ResponseEntity readAllUsers(Pageable pageable){
        PageResponseDTO<UsersResponseDTO> allUsersPage = adminService.readAllUsers(pageable);
        return new ResponseEntity(allUsersPage, HttpStatus.OK);
    }

    //유저 상태 변경
    @PutMapping("/admin/users/{usersid}")
    public ResponseEntity updateUsersStatus(@RequestBody UsersRequestDTO usersRequestDTO,
                                             @PathVariable("usersid") Long usersId){
        adminService.updateUsersStatus(usersRequestDTO, usersId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

    //사이트 전체글 보기
    @GetMapping("/admin/issue")
    public ResponseEntity readAllIssue(Pageable pageable){
        PageResponseDTO<MyIssueResponseDTO> allIssuePage = adminService.readAllIssue(pageable);
        return new ResponseEntity(allIssuePage, HttpStatus.OK);
    }

    //글 권한 수정
    @PutMapping("/admin/issue/{usersid}")
    public ResponseEntity updateUsersStatus(@RequestBody Map<String, Boolean> updatePub,
                                            @PathVariable("usersid") Long usersId){
        boolean update = updatePub.get("publicStatus");
        adminService.updateIssuePub(usersId, update);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
}
