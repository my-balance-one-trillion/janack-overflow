package com.example.janackoverflow.admin.controller;

import com.example.janackoverflow.admin.service.AdminService;
import com.example.janackoverflow.global.pagination.PageResponseDTO;
import com.example.janackoverflow.mypage.domain.response.MyIssueResponseDTO;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.repository.UsersRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "관리자 Controller", description = "관리자 관련 API")
@RestController
public class AdminController {
    private final AdminService adminService;
    private final UsersRepository usersRepository;

    public AdminController(AdminService adminService,
                           UsersRepository usersRepository){
        this.adminService = adminService;
        this.usersRepository = usersRepository;
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
//    전체 글댓글 갯수
    @GetMapping("/admin/count")
    public ResponseEntity readAllCount(){
        List<Long> allCount = adminService.readAllCount();
        return new ResponseEntity(allCount,HttpStatus.OK);
    }

    @GetMapping("/admin/signtrend")
    public ResponseEntity readSign6Month(){
        List<Map<String, Object>> signTrend = adminService.readSign6Month();
        return new ResponseEntity(signTrend,HttpStatus.OK);
    }

    @GetMapping("/admin/solutiontrend")
    public ResponseEntity readSolution6Month(){
        List<Map<String, Object>> solutionTrend = adminService.readSolution6Month();
        return new ResponseEntity(solutionTrend,HttpStatus.OK);
    }
}
