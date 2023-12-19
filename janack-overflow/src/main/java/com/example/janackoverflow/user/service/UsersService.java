package com.example.janackoverflow.user.service;

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

        if(optionalUsers.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public boolean isDuplicatedEmail(UsersRequestDTO usersRequestDTO){ //이메일 중복 확인
        Users users = usersRequestDTO.toEntity();

        Optional<Users> optionalUsers = usersRepository.findByEmail(users.getEmail()); //조회했는데, 있으면

        if(optionalUsers.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public Users createUser(UsersRequestDTO usersRequestDTO){ //회원 생성
        Users users = usersRequestDTO.toEntity();

        //패스워드 암호화
        users.updatePassword(passwordEncoder.encode(users.getPassword()));

        return usersRepository.save(users);
    }

    public UsersResponseDTO readUser(long id){ //회원 하나 읽기
        Users users = usersRepository.findById(id).orElseThrow(RuntimeException::new);
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
                .build();
    }

    public void updateRandomPass(UsersRequestDTO usersRequestDTO, String pass){ // 임시 패스워드로 업데이트
        Users users = usersRepository.findByEmail(usersRequestDTO.getEmail()).orElseThrow(RuntimeException::new);
        Users updatePassUser = users.toBuilder()
                .password(passwordEncoder.encode(pass))
                .build();
        usersRepository.save(updatePassUser);
    }

    public Users findByEmail(String email) { //UserDetails loadUserByUsername() 전용 서비스
        System.out.println("findByEmail start! email : " + email);
        return usersRepository.findByEmail(email).get();
    }

}
