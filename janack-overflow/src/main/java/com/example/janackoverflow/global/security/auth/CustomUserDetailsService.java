package com.example.janackoverflow.global.security.auth;

import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsersService usersService;

    @Autowired
    public CustomUserDetailsService(UsersService usersService){
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //사용자 Role에 따라 두 가지 역할을 부여하는 코드를 작성했습니다.

        System.out.println("loging email : " + email);
        Users findUser = usersService.findUser(email).orElseThrow(()-> new RuntimeException());

        if ("user".equals(findUser.getRole())) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(findUser.getNickname()) //유저 이름
                    .password(findUser.getPassword()) // 암호화된 "password"
                    .roles("USER")
                    .build();
        } else if ("admin".equals(findUser.getRole())) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(findUser.getNickname()) //유저 이름
                    .password(findUser.getPassword()) // 암호화된 "password"
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("해당 정보를 가진 유저 정보가 존재하지 않습니다 : " + email);
        }

    }
}
