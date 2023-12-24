package com.example.janackoverflow.user.service;

import com.example.janackoverflow.global.exception.BusinessLogicException;
import com.example.janackoverflow.global.exception.ExceptionCode;
import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public boolean isDuplicatedNick(UsersRequestDTO usersRequestDTO) { //닉네임 중복 확인
        Users users = usersRequestDTO.toEntity();

        Optional<Users> optionalUsers = usersRepository.findByNickname(users.getNickname()); //조회했는데, 있으면

        return optionalUsers.isPresent();

    }

    public boolean isDuplicatedEmail(UsersRequestDTO usersRequestDTO){ //이메일 중복 확인
        Users users = usersRequestDTO.toEntity();

        Optional<Users> optionalUsers = usersRepository.findByEmail(users.getEmail()); //조회했는데, 있으면

        return optionalUsers.isPresent();

    }

    public void createUser(UsersRequestDTO usersRequestDTO){ //회원 생성
        Users users = usersRequestDTO.toEntity();

        //패스워드 암호화
        users.updatePassword(passwordEncoder.encode(users.getPassword()));

        usersRepository.save(users);
    }

    public UsersResponseDTO readUser(long id){ //회원 하나 읽기
        Users users = usersRepository.findById(id).orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return UsersResponseDTO.builder()
                .id(id)
                .email(users.getEmail())
                .digit(users.getDigit())
                .birth(users.getBirth())
                .name(users.getName())
                .createdAt(users.getCreatedAt())
                .nickname(users.getNickname())
                .profileImage(users.getProfileImage())
                .holder(users.getHolder())
                .bankName(users.getBankName())
                .outputAcntNum(users.getOutputAcntNum())
                .role(users.getRole())
                .build();
    }

    public void updateRandomPass(UsersRequestDTO usersRequestDTO, String pass){ // 임시 패스워드로 업데이트
        Users users = this.findByEmail(usersRequestDTO.getEmail());

        Users updatePassUser = users.toBuilder()
                .password(passwordEncoder.encode(pass))
                .build();

        usersRepository.save(updatePassUser);
    }

    public Users findByEmail(String email) { //UserDetails loadUserByUsername() 에 중요한 리포지토리

        System.out.println("findByEmail start! email : " + email);

        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.UNAUTHORIZED));
    }

}
