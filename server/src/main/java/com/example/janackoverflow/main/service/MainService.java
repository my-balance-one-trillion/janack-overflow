package com.example.janackoverflow.main.service;

import com.example.janackoverflow.community.service.LikesService;
import com.example.janackoverflow.issue.domain.IssueDTO;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.issue.entity.Issue;
import com.example.janackoverflow.issue.repository.IssueRepository;
import com.example.janackoverflow.main.domain.NowAccountResponseDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MainService {

    @Autowired
    private InputAccountRepository inputAccountRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private LikesService likesService;

    //적금 호출 status= "01", user 같은 경우
    public NowAccountResponseDTO readNowAccount(Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found :"+userId));


        InputAccount inputAccount = inputAccountRepository.findByUsersIdAndStatus(userId, "01")
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        Long savingCnt = issueRepository.countByUsersIdAndInputAccountIdAndStatus(userId, inputAccount.getId(), "03");
        int dayCnt = LocalDateTime.now().getDayOfYear()-inputAccount.getCreatedAt().getDayOfYear()+1;
        return NowAccountResponseDTO.toDto(inputAccount, savingCnt, dayCnt);


    }
    public NowAccountResponseDTO readRecentAccount(Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found :"+userId));


        InputAccount inputAccount = inputAccountRepository.findTop1ByUsersIdAndStatusOrderByCreatedAtDesc(userId, "03")
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        Long savingCnt = issueRepository.countByUsersIdAndInputAccountIdAndStatus(userId, inputAccount.getId(), "03");

        int dayCnt = LocalDateTime.now().getDayOfYear()-inputAccount.getCreatedAt().getDayOfYear()+1;
        return NowAccountResponseDTO.toDto(inputAccount, savingCnt, dayCnt);

    }
//    최근 일주일 이슈
    public List<IssueResponseDTO> readWeeklyIssues(Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found :"+userId));

        return issueRepository.findByUsersIdAndCreatedAtAfterOrderByCreatedAtDesc(userId, LocalDateTime.now().minusWeeks(1).withHour(0).withMinute(0).withSecond(0))
                .stream().map(IssueResponseDTO::toDto)
                .toList();
    }
    //최근 이슈 10개
    public List<IssueDTO.ResponseDTO> readRecent10Issues(){
//        return issueRepository.findTop10ByOrderByCreatedAtDesc()
//                .stream().map(IssueResponseDTO::toDto)
//                .toList();
        return issueRepository.findTop10ByPublicStatusOrderByCreatedAtDesc(true)
                .stream().map(issue -> issue.toDto(likesService.getIssueLikes(issue.getId()), usersRepository.findById(issue.getUsers().getId()).get().toIssueDto()))
                .toList();
    }

}
