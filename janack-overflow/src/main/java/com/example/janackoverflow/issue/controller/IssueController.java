package com.example.janackoverflow.issue.controller;

import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.service.IssueService;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // 에러 등록
    @PostMapping
    public ResponseEntity<?> createIssue(@RequestBody IssueDTO.RequestDTO issueRequestDTO){
        Users users = new Users();
        users.setId(1L);

        Issue issue =  issueService.createIssue(issueRequestDTO, users);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }
    
    // 에러 등록 시간
    @GetMapping("/{issueId}/created-at")
    public ResponseEntity<LocalDateTime> getIssueCreatedAt(@PathVariable Long issueId){
        LocalDateTime createdAt = issueService.getIssueCreatedAt(issueId);
        return new ResponseEntity<>(createdAt, HttpStatus.OK);
    }

}
