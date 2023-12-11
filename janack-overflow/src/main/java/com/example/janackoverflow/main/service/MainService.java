package com.example.janackoverflow.main.service;

import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MainService {

    @Autowired
    private InputAccountRepository inputAccountRepository;

    @Autowired
    private UsersRepository usersRepository;

    //적금 호출 status= "01", user 같은 경우
    public InputAccount readNowAccount(Long userId){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found :"+userId));
        List<InputAccount> inputAccount = inputAccountRepository.findByUsersIdAndStatus(userId, "01");

        return inputAccount.get(0);
    }
    //최근 일주일 이슈
//    public List<Issue> readWeeklyIssues(Long userId){
//
//    }
    //비로그인 시 커뮤니티


}
