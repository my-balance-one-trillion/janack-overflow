package com.example.janackoverflow.global.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    // 로그아웃 성공 이후의 동작을 정의한 구현체를 반환

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 리다이렉트 <- http header 에서 로그아웃을 수행하여 주석처리
        //response.sendRedirect(String.valueOf());
    }
}
