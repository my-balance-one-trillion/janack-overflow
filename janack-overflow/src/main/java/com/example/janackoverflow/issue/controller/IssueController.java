package com.example.janackoverflow.issue.controller;

import com.example.janackoverflow.issue.domain.request.CreateIssueRequestDTO;
import com.example.janackoverflow.issue.domain.request.CreateSolutionRequestDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.domain.response.StackOverflowResponse;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import com.example.janackoverflow.issue.service.IssueService;
import com.example.janackoverflow.issue.service.SolutionService;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final SolutionService solutionService;

    public IssueController(IssueService issueService, SolutionService solutionService) {
        this.issueService = issueService;
        this.solutionService = solutionService;
    }

    // 에러 등록
    @PostMapping
    public ResponseEntity<?> createIssue(@Validated @RequestBody CreateIssueRequestDTO issueRequestDTO, BindingResult bindingResult){
        // 회원번호 1번으로 강제 설정
        Users users = new Users();
        users.setId(3L);

        if(bindingResult.hasErrors()){
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        Issue issue =  issueService.createIssue(issueRequestDTO, users);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }
    
    /*// 에러 등록 시간
    @GetMapping("/{issueId}/created-at")
    public ResponseEntity<LocalDateTime> getIssueCreatedAt(@PathVariable Long issueId){
        LocalDateTime createdAt = issueService.getIssueCreatedAt(issueId);
        return new ResponseEntity<>(createdAt, HttpStatus.OK);
    }*/

    // 에러 조회 (에러 제목, 키워드, 카테고리, 코드, 생성 날짜)
    @GetMapping("/{issueId}")
    public ResponseEntity<?> getIssue(@PathVariable Long issueId){
        Optional<IssueResponseDTO> issue = issueService.getIssueById(issueId);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    // 에러 키워드로 stackoverflow 검색
    @GetMapping("/{issueId}/keyword")
    public ResponseEntity<List<StackOverflowResponse>> searchStackOverflow(@PathVariable Long issueId) throws JsonProcessingException {
        List<StackOverflowResponse> searchResult = issueService.searchByKeyword(issueId);
        if(searchResult.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    // 해결 등록
    @PostMapping("/{issueId}/solution")
    public ResponseEntity<?> createSolution(@Validated @RequestBody CreateSolutionRequestDTO solutionRequestDTO, @PathVariable Long issueId, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        Solution solution = solutionService.createSolution(solutionRequestDTO, issueId);
        return new ResponseEntity<>(solution, HttpStatus.CREATED);
    }

    // 에러 해결 포기
    @PatchMapping("{issueId}/giveup")
    public ResponseEntity<?> giveUpIssue(@PathVariable Long issueId){
        Issue issue = issueService.updateIssueStatus(issueId);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }
}
