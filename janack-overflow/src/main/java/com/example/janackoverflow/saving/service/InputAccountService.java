package com.example.janackoverflow.saving.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.issue.domain.response.IssueResponseDTO;
import com.example.janackoverflow.saving.domain.request.InputAccountRequestDTO;
import com.example.janackoverflow.saving.domain.request.SavingRequestDTO;
import com.example.janackoverflow.saving.domain.response.InputAccountResponseDTO;
import com.example.janackoverflow.saving.entity.InputAccount;
import com.example.janackoverflow.saving.entity.Rule;
import com.example.janackoverflow.saving.repository.InputAccountRepository;
import com.example.janackoverflow.saving.repository.RuleRepository;
import com.example.janackoverflow.user.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InputAccountService {

    private final InputAccountRepository inputAccountRepository;
    public InputAccountService(InputAccountRepository inputAccountRepository){
        this.inputAccountRepository = inputAccountRepository;
    }


    // 입금 계좌(적금 계좌) 개설
    @Transactional
    public InputAccount createInputAccount(InputAccountRequestDTO inputAccountRequestDTO, Users users) {

        if(inputAccountRepository.findByUsersIdAndStatus(users.getId(), "01").isPresent()){
            throw new BusinessLogicException(ExceptionCode.ACCOUNT_EXIST);
        }

        InputAccount inputAccount = inputAccountRequestDTO.toEntity(users);
        InputAccount savedInputAccount = inputAccountRepository.save(inputAccount);

        createAcntNum(savedInputAccount);  // 계좌 번호 생성

        return savedInputAccount;
    }
    // 계좌 번호 생성
    private void createAcntNum(InputAccount inputAccount){
        Long id = inputAccount.getId();
        inputAccount.updateAcntNum(""+(id+3020000009687L));
    }

    // 현재 진행 중인 계좌 정보만 조회
    @Transactional(readOnly = true)
     public Optional<InputAccountResponseDTO> getInProgressAccountByUser(Users users) {
        return Optional.ofNullable(inputAccountRepository.findByUsersIdAndStatus(users.getId(), "01")
                .map(InputAccountResponseDTO::toDto)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND)));
    }

    // 사용자의 모든 입금 계좌(적금 계좌) 조회
    @Transactional(readOnly = true)
    public List<InputAccountResponseDTO> getAccountsByUser(Users users){
        List<InputAccount> allAccountsByUser =  inputAccountRepository.findByUsersIdOrderByCreatedAtDesc(users.getId());

        if (allAccountsByUser.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return allAccountsByUser.stream()
                .map(InputAccountResponseDTO::toDto)
                .collect(Collectors.toList());
    }

    // 계좌 정보 수정
    @Transactional
    public InputAccount updateInputAccount(InputAccountRequestDTO inputAccountRequestDTO, Users users) {
        InputAccount updateAccount = inputAccountRepository.findByUsersIdAndStatus(users.getId(), "01")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));

        // 적금 정보 수정 (적금 이름, 적금 목표명, 적금 목표 금액)
        updateAccount.updateInputAccount(inputAccountRequestDTO.getAcntName(),
                inputAccountRequestDTO.getGoalName(),
                inputAccountRequestDTO.getGoalAmount());

        return inputAccountRepository.save(updateAccount);
    }

    // 현재 진행 중인 적금 상태 변경
    @Transactional
    public InputAccount deleteInputAccount(Long userId) {
        InputAccount deleteAccount = inputAccountRepository.findByUsersIdAndStatus(userId, "01")
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ACCOUNT_NOT_FOUND));

        deleteAccount.updateStatus("02");  // 적금 포기 상태로 변경
        return inputAccountRepository.save(deleteAccount);
    }
}
