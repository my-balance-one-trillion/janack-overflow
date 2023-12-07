package com.example.janackoverflow.saving.service;

import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InputAccountService {

    private final InputAccountRepository inputAccountRepository;
    public InputAccountService(InputAccountRepository inputAccountRepository){
        this.inputAccountRepository = inputAccountRepository;
    }


    // 입금 계좌(적금 계좌) 생성
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

        return inputAccountRepository.save(inputAccountRequestDTO.toEntity(users));
    }

    // 사용자의 모든 입금 계좌(적금 계좌) 조회
    @Transactional(readOnly = true)
    public List<InputAccount> getAccountsByUser(long id){
        return inputAccountRepository.findByUsersId(id);
    }

}
