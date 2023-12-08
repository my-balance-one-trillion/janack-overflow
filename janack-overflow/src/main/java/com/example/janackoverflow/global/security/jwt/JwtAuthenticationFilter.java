package com.example.janackoverflow.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.janackoverflow.global.security.LoginRequestDTO;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Date;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {//UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
			new AntPathRequestMatcher("/login", "POST");
									// '/login' URL 접근 시 필터 실행

	@Autowired
	private UsersService usersService;


	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {

		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);

		System.out.println("JwtAuthenticationFilter 초기화, AuthenticationManager 객체 생성");

		this.authenticationManager = authenticationManager;
		// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("JwtAuthenticationFilter : 진입");
		
		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		ObjectMapper om = new ObjectMapper();
		LoginRequestDTO loginRequestDto = null;
		
		try {
			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("JwtAuthenticationFilter : " + loginRequestDto);
		
		// 유저네임, 패스워드 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getEmail(),
						loginRequestDto.getPassword());
		
		System.out.println("JwtAuthenticationFilter : 토큰 생성 완료");
		
		// authenticate() 함수가 호출 되면 AuthenticationProvider가 UserDetailsService 객체의
		// loadUserByUsername(토큰의 첫 번째 파라미터 값) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두 번째 파라미터(credential)값과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: AuthenticationProvider의 디폴트 서비스는 UserDetailsService 타입
		// Tip: AuthenticationProvider의 디폴트 암호화 방식은 BCryptPasswordEncoder 타입

		Authentication authentication =
				authenticationManager.authenticate(authenticationToken);
		
		UserDetails principalDetailis = (UserDetails) authentication.getPrincipal();

		System.out.println("Authentication : " + principalDetailis.getUsername());

		return authentication;
	}

	// attemptAuthentication()의 호출 결과로 Authentication 객체 리턴시 successfulAuthentication() 의 호출 결과를 리턴함

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("successful Authentication");
		
		UserDetails principalDetailis = (UserDetails) authResult.getPrincipal();

		System.out.println("로그인 시도 계정 : " + principalDetailis.getUsername());

		// JWT Token 생성해서 response에 담아주기
		String jwtToken = JWT.create()
				.withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) //토큰 유효시간
				.withClaim("email", principalDetailis.getUsername()) //email값
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); //시크릿 키 이용하여 HMAC512 알고리즘 적용

		response.addHeader("Authorization", JwtProperties.TOKEN_PREFIX + jwtToken);

		System.out.println("login complete");
	}
	
}
