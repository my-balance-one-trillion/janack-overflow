package com.example.janackoverflow.issue.controller;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.SolutionDTO;
import com.example.janackoverflow.issue.domain.request.CreateIssueRequestDTO;
import com.example.janackoverflow.issue.domain.request.CreateSolutionRequestDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.domain.response.SolutionResponseDTO;
import com.example.janackoverflow.issue.domain.response.StackOverflowResponse;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.entity.Solution;
import com.example.janackoverflow.issue.service.IssueService;
import com.example.janackoverflow.issue.service.SolutionService;
import com.example.janackoverflow.user.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> createIssue(@Validated @RequestBody CreateIssueRequestDTO issueRequestDTO,
                                         BindingResult bindingResult, @AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
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

    // 에러 조회
    @GetMapping
    public ResponseEntity<?> getIssue(@AuthenticationPrincipal NowUserDetails userDetails) throws JsonProcessingException {
        Users users = userDetails.getUser();
        Optional<IssueResponseDTO> issue = issueService.getIssueByUserId(users);
        IssueResponseDTO userIssue = issue.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));

        // 에러 키워드로 stackoverflow 검색
        List<StackOverflowResponse> searchResult = issueService.searchByKeyword(userIssue.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("issue", issue.get());
        response.put("stackOverflowResults", searchResult);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 해결 등록
    @PostMapping("/solution")
    public ResponseEntity<?> createSolution(@Validated @RequestBody CreateSolutionRequestDTO solutionRequestDTO,
                                            @AuthenticationPrincipal NowUserDetails userDetails, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        Users users = userDetails.getUser();
        Optional<IssueResponseDTO> issue = issueService.getIssueByUserId(users);
        IssueResponseDTO userIssue = issue.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));

        Solution solution = solutionService.createSolution(solutionRequestDTO, userIssue.getId());
        return new ResponseEntity<>(solution, HttpStatus.CREATED);
    }

    // 에러 해결 포기
    @PutMapping("/giveup")
    public ResponseEntity<?> giveUpIssue(@AuthenticationPrincipal NowUserDetails userDetails){
        Users users = userDetails.getUser();
        Optional<IssueResponseDTO> issue = issueService.getIssueByUserId(users);
        IssueResponseDTO userIssue = issue.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ERROR_NOT_FOUND));

        Issue giveupIssue = issueService.updateIssueStatus(userIssue.getId());
        return new ResponseEntity<>(giveupIssue, HttpStatus.OK);
    }

    // 에러 해결 결과 조회
    @GetMapping("/solution/{solutionId}")
    public ResponseEntity<?> getSolution(@PathVariable(name = "solutionId") Long solutionId){
        SolutionResponseDTO solution = solutionService.getRecentSolution(solutionId);
        IssueResponseDTO issue = issueService.getIssue(solution.getIssueId());

        Map<String, Object> response = new HashMap<>();
        response.put("issue", issue);
        response.put("solution", solution);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
