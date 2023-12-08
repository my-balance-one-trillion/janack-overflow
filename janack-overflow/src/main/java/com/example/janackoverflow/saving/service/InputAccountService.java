package com.example.janackoverflow.saving.service;

import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.saving.repository.RuleRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InputAccountService {

    private final InputAccountRepository inputAccountRepository;
    private final RuleRepository ruleRepository;
    public InputAccountService(InputAccountRepository inputAccountRepository, RuleRepository ruleRepository){
        this.inputAccountRepository = inputAccountRepository;
        this.ruleRepository = ruleRepository;
    }


    // 입금 계좌(적금 계좌) 개설
    @Transactional
    public InputAccount createInputAccount(InputAccountRequestDTO inputAccountRequestDTO, Users users) {
        if (inputAccountRequestDTO.getAcntName().isEmpty()) {
            throw new IllegalArgumentException("적금명 입력");
        }
        if (inputAccountRequestDTO.getGoalName().isEmpty()) {
            throw new IllegalArgumentException("목표명 입력");
        }
        if (inputAccountRequestDTO.getGoalAmount() < 1000) {
            throw new IllegalArgumentException("목표 금액은 1000원 이상");
        }

        // 회원 정보 확인
//        Users users = userRepository.findById(users);

        InputAccount inputAccount = inputAccountRequestDTO.toEntity(users);
        InputAccount savedInputAccount = inputAccountRepository.save(inputAccount);
        
        createAcntNum(savedInputAccount);  // 계좌 번호 생성
        
        return savedInputAccount;
    }
    // 계좌 번호 생성
    private void createAcntNum(InputAccount inputAccount){
        Long id = inputAccount.getId();
        inputAccount.setAcntNum(""+(id+3020000009687L));
    }

    // 현재 진행 중인 계좌 정보만 조회
     public Optional<InputAccount> getInProgressAccountByUser(long userId) {
        return inputAccountRepository.findByUsersIdAndStatus(userId, "01");
    }

    // 사용자의 모든 입금 계좌(적금 계좌) 조회
    @Transactional(readOnly = true)
    public List<InputAccount> getAccountsByUser(long id){
        return inputAccountRepository.findByUsersId(id);
    }

    // 계좌 정보 수정
    @Transactional
    public InputAccount updateInputAccount(InputAccountRequestDTO inputAccountRequestDTO, long userId) {
        InputAccount updateAccount = inputAccountRepository.findByUsersIdAndStatus(userId, "01")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "진행 중인 적금 없음"));

        updateAccount.setAcntName(inputAccountRequestDTO.getAcntName());
        updateAccount.setGoalName(inputAccountRequestDTO.getGoalName());
        updateAccount.setGoalAmount(inputAccountRequestDTO.getGoalAmount());

        return inputAccountRepository.save(updateAccount);
    }
}
