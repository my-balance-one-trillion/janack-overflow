package com.example.janackoverflow.global.security.auth;

import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //사용자 Role에 따라 두 가지 역할을 부여하는 코드를 작성했습니다.

        System.out.println("loging email : " + email);

        Users findUser = usersService.findByEmail(email);

        if ("USER".equals(findUser.getRole())) {
            return new NowUserDetails(findUser);
        } else if("ADMIN".equals(findUser.getRole())) {
            return new NowUserDetails(findUser);
        } else {
            throw new UsernameNotFoundException("해당 정보를 가진 유저 정보가 존재하지 않습니다 : " + email);
        }

    }
}
