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
        // "/" 로 리다이렉트
        String scheme = request.getScheme(); //http 또는 https
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        response.sendRedirect(scheme + "://" + serverName + ":" + serverPort + "/");
    }
}