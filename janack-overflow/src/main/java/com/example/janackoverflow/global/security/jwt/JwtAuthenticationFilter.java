package com.example.janackoverflow.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.janackoverflow.global.security.DTO.LoginRequestDTO;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {//UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
			new AntPathRequestMatcher("/login", "POST");
									// '/login' URL 접근 시 필터 실행

	@Autowired
	private JwtProperties jwtProperties;

	@Autowired
	private UsersService usersService;

	private LoginRequestDTO loginRequestDTO;


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

		try {
			loginRequestDTO = om.readValue(request.getInputStream(), LoginRequestDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			usersService.findByEmail(loginRequestDTO.getEmail()); //입력받은 사용자와 DB일치 여부 확인
		} catch (NoSuchElementException e){

			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain"); // MIME 타입 설정
			response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

			writer.println("입력하신 Email과 일치하는 사용자가 없습니다");

			return null;
		}
		
		// 유저네임 패스워드 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						loginRequestDTO.getEmail(),
						loginRequestDTO.getPassword());
		
		System.out.println("JwtAuthenticationFilter : authenticationToken 생성");
		
		// authenticate() 함수가 호출 되면 AuthenticationProvider가 UserDetailsService 객체의
		// loadUserByUsername(토큰의 첫 번째 파라미터 값) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두 번째 파라미터(credential)값과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: AuthenticationProvider의 디폴트 서비스는 UserDetailsService 타입
		// Tip: AuthenticationProvider의 디폴트 암호화 방식은 BCryptPasswordEncoder 타입

		try{

			Authentication authentication =
					authenticationManager.authenticate(authenticationToken);

			//로그인 시도하는 유저의 상태 조회
			String statusNum = usersService.findByEmail(loginRequestDTO.getEmail()).getStatus();

			if(!statusNum.equals("01")){ // 상태 01이 아니면
				//인가 과정 중단

				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("text/plain"); // MIME 타입 설정
				response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				switch (statusNum){
					case "02" : {
						writer.println("탈퇴 처리된 사용자 입니다");
						break;
					}

					case "03" : {
						writer.println("정지된 사용자 입니다");
						break;
					}
				}

				return null; //null을 반환하여 인가 과정 중단
			}

			NowUserDetails principalDetailis = (NowUserDetails) authentication.getPrincipal();

			System.out.println("Authentication Id : " + principalDetailis.getId());

			return authentication;

		} catch (AuthenticationException e){ //인증 실패 예외 - 여기선 패스워드 입력에 실패한 경우를 의미
			
			System.out.println("인가 실패");

			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain"); // MIME 타입 설정
			response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

			writer.println("패스워드가 일치하지 않습니다");

			return null;
		}

	}

	// attemptAuthentication()의 호출 결과로 Authentication 객체 리턴시 successfulAuthentication() 의 호출 결과를 리턴함

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("successful Authentication");
		
		NowUserDetails principalDetailis = (NowUserDetails) authResult.getPrincipal();

		// JWT Token 생성해서 response에 담아주기
		String jwtToken = JWT.create()
				.withSubject(principalDetailis.getUsername()) //현재 주체가 되는 사용자 (Subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getTokenValidityInSeconds() * 1000)) //토큰 유효시간
				.withClaim("email", principalDetailis.getUsername()) //email값
				.sign(Algorithm.HMAC512(jwtProperties.getSecret1())); //시크릿 키 이용하여 HMAC512 알고리즘 적용

		//헤더에 접근 권한 정보를 추가해 브라우저에서 차단하지 못하게 방지
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With, Pragma");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");

		//헤더에 토큰값 추가
		response.addHeader(jwtProperties.getHeader(), "Bearer " + jwtToken);

		System.out.println("jwtToken : " + jwtToken);

		System.out.println("login complete");

//		Enumeration<String> headerNames = request.getHeaderNames();
//
//		while (headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			String headerValue = request.getHeader(headerName);
//
//			System.out.println(headerName + ": " + headerValue);
//		}

		// 리다이렉트 <- /login 페이지 진입 하기 전의 페이지 정보가 들어오지 않아서 주석처리
		//response.sendRedirect(String.valueOf());

	}
	
}
