package com.example.janackoverflow.user.service;

import com.example.janackoverflow.user.domain.request.UsersRequestDTO;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    public Users createUser(UsersRequestDTO usersRequestDTO){
        Users users = usersRequestDTO.toEntity();
        return usersRepository.save(users);
    }

    public void updateUser(UsersRequestDTO usersRequestDTO, Long id){
        Users users = usersRepository.findById(id).orElseThrow(RuntimeException::new);
        if(users.getPassword().equals(usersRequestDTO.getPassword())){
            users.updateDigit(usersRequestDTO.getDigit());
            users.updateBirth(usersRequestDTO.getBirth());
            users.updateName(usersRequestDTO.getName());
            users.updateNickname(usersRequestDTO.getNickname());
            if(usersRequestDTO.getNewPassword() != null){
                if(usersRequestDTO.getNewPassword().equals(usersRequestDTO.getNewPasswordConfirm())){
                    users.updatePassword(usersRequestDTO.getNewPassword());
                }else{
                    throw new RuntimeException("패스워드와 패스워드확인이 다릅니다.");
                }
            usersRepository.save(users);
            }
        }else{
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }
    }
}
