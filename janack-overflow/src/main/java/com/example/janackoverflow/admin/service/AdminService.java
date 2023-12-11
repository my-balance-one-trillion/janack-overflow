package com.example.janackoverflow.admin.service;

import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.domain.response.UsersResponseDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UsersRepository usersRepository;

    public AdminService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

//    유저 전체보기 -> 유저를 다 불러온다->그러려면 유저 객체를 아니 dto를 받아와서 생성해야한다
//    합당한 repo메서드를 만들었으므로 서비스 메서드를 만들자
    public Page<UsersResponseDTO> readAllUsers(){
        Pageable pageable = PageRequest.of(0,5, Sort.by("createdAt").descending());
        Page<Users> allUsers = usersRepository.findAll(pageable);
        List<UsersResponseDTO> allUsersList = allUsers.stream().map(users -> UsersResponseDTO.builder()
                .id(users.getId())
                .email(users.getEmail())
                .nickname(users.getNickname())
                .createdAt(users.getCreatedAt())
                .role(users.getRole())
                .status(users.getStatus())
                .build()).toList();
        Page<UsersResponseDTO> allUsersPage = new PageImpl<>(allUsersList, pageable, allUsers.getTotalPages());
        return allUsersPage;
    }

    //유저 상태 변경이므로 이미지 바꾸는거랑 같은 로직이면 될듯
    public void updateUsersStatus(UsersRequestDTO usersRequestDTO, Long usersId){
        Users users = usersRepository.findById(usersId).orElseThrow(RuntimeException::new);
        Users updatedUsers = users.toBuilder()
                .status(usersRequestDTO.getStatus())
                .build();
        usersRepository.save(updatedUsers);
    }
}
