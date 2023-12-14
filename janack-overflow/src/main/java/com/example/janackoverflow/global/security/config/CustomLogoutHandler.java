package com.example.janackoverflow.global.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.janackoverflow.global.security.jwt.JwtProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.Date;

public class CustomLogoutHandler extends AbstractAuthenticationProcessingFilter implements LogoutHandler {
    // 실제 로그아웃 처리를 담당하는 구현체

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    private final AuthenticationManager authenticationManager;

    protected CustomLogoutHandler(AuthenticationManager authenticationManager) {
        super("/logout" , authenticationManager);

        System.out.println("로그아웃에 위치한 authenticationManager 생성");

        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //시큐리티 컨텍스트 초기화
        SecurityContextHolder.clearContext();

        //가짜토큰을 발급하여 기존에 발급한 토큰을 무효화 시킬겁니다
        //가짜 토큰 발급
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        "LiarLiar",
                        "lielielieLielieLie");

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        //가짜 유저 정보를 주체로 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return authentication;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String jwtToken = JWT.create()
                .withSubject("Liar") //현재 주체가 될 가짜 사용자 (Subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getTokenValidityInSeconds() * 1000)) //토큰 유효시간
                .withClaim("Lielielielielielie", "lielielielieLielielie") //가짜 email값
                .sign(Algorithm.HMAC512(jwtProperties.getSecret2())); //시크릿 키 이용하여 HMAC512 알고리즘 적용

        response.addHeader("token?", jwtToken);
    }
}
