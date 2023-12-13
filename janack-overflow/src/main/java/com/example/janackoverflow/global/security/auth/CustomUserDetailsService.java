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

    @Autowired
    public CustomUserDetailsService(UsersService usersService){
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("now loadUser email : " + email);

        try{

            Users findUser = usersService.findByEmail(email);

            return new NowUserDetails(findUser);

        } catch (Exception e){
            throw new UsernameNotFoundException("해당 정보를 가진 유저가 존재하지 않습니다 : " + email);
        }

    }
}
