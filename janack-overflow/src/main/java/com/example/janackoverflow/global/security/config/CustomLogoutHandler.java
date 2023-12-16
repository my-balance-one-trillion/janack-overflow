package com.example.janackoverflow.global.security.config;

import com.example.janackoverflow.global.security.jwt.JwtProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {
    // 실제 로그아웃 처리를 담당하는 구현체

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //시큐리티 컨텍스트 초기화
        SecurityContextHolder.clearContext();

    }
}
