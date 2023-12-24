package com.example.janackoverflow.main.controller;

import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.main.domain.NowAccountResponseDTO;
import com.example.janackoverflow.main.service.MainService;
import com.example.janackoverflow.saving.entity.InputAccount;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
@Slf4j
public class MainController {

    @Autowired
    MainService mainService;

    //비로그인 시 메인페이지
    @GetMapping("/unlogin")
    private ResponseEntity<?> getUnLoggedInMainPage(){
        List<IssueDTO.ResponseDTO> issueResponseDTOList = mainService.readRecent10Issues();
        if(!issueResponseDTOList.isEmpty()) {
            return ResponseEntity.ok(issueResponseDTOList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    //로그인 시 메인페이지
    @GetMapping("/login")
    private ResponseEntity<?> getLoggedInMainPage(@AuthenticationPrincipal NowUserDetails nowUserDetails){

        if(nowUserDetails.getUser() == null){
            return ResponseEntity.badRequest().build();
        }
        Long userId = nowUserDetails.getUser().getId();

        try{
            NowAccountResponseDTO nowAccountResponseDTO = mainService.readNowAccount(userId);
            List<IssueResponseDTO> issueResponseDTOList = mainService.readWeeklyIssues(userId);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("nowAccount", nowAccountResponseDTO);
            responseMap.put("weeklyIssues", issueResponseDTOList);
            return ResponseEntity.ok(responseMap);
        } catch (IllegalArgumentException e1){
            try{
                NowAccountResponseDTO recentAccountDTO = mainService.readRecentAccount(userId);
                List<IssueResponseDTO> issueResponseDTOList = mainService.readWeeklyIssues(userId);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("nowAccount", recentAccountDTO);
                responseMap.put("weeklyIssues", issueResponseDTOList);
                return ResponseEntity.ok(responseMap);
            } catch (IllegalArgumentException e2){
                return ResponseEntity.noContent().build();
            }

        }
    }

}
