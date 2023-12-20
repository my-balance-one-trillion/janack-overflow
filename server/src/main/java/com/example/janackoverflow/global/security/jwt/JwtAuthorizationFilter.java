package com.example.janackoverflow.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	//요청 헤더의 아이디와 패스워드를 파싱하여 인증 요청을 위임하는 필터
	//인증 성공 여부에 따라 핸들러 실행

	@Autowired
	private JwtProperties jwtProperties;

	@Autowired
	private UsersRepository usersRepository;

	//토큰 인증에서 제외할 url
	private static final List<String> EXCLUDE_URL =
			Collections.unmodifiableList(
					Arrays.asList(
							"/",
							"/static/**",
							"/favicon.ico",
							"/signup",
							"/login"
					));

	// Filter에서 제외할 URL 설정 <- 이거 안하면 로그인도 막힐거 같아서 걸어둠
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		String header = request.getHeader(jwtProperties.getHeader()); //헤더 추출

		//추출된 헤더가 없거나 헤더에 정보가 없으면 필터 체인지 실행하고 종료
		if(header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		System.out.println("현재 header : " + header);

		String token = header.replace("Bearer ", "");

//		try {
			// 토큰 검증 (이 절차 때문에 AuthenticationManager 불러올 필요 없음)
			// SecurityContext에 직접 접근하여 세션을 만들면 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨
			String username = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret1()))
					.build()
					.verify(token)
					.getClaim("email")
					.asString();

			System.out.println("doFilterInternal : " + username);

			if (username != null) {
				Users users = usersRepository.findByEmail(username).orElseThrow(
						() -> new RuntimeException("Can`t find Claim userEmail " + username));

				// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
				// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
				NowUserDetails principalDetails = new NowUserDetails(users);

				System.out.println(principalDetails.getAuthorities());

				Authentication authentication =
						new UsernamePasswordAuthenticationToken(
								principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
								null, // 패스워드는 null 처리, 어차피 지금 인증하는게 아니니까!!
								principalDetails.getAuthorities());

				// 강제로 시큐리티의 세션에 접근하여 값 저장
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

//		} catch (ExpiredJwtException e){ //토큰 유효시간이 만료될 경우
//			String header2 = request.getHeader("refresh"); //리프레시 토큰 추출
//
//			if(header2 == null || !header2.startsWith("Bearer ")) { //존재하지 않으면
//				String scheme = request.getScheme(); //http 또는 https
//				String serverName = request.getServerName();
//				int serverPort = request.getServerPort();
//
//				//강제 로그인 페이지로 이동
//				response.sendRedirect(scheme + "://" + serverName + ":" + serverPort + "/login");
//				return;
//			}
//
//			//리프레시 토큰 검증
//			String refresh = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret2()))
//					.build()
//					.verify(token)
//					.getClaim("refresh")
//					.asString();
//
//			System.out.println("refresh : " + refresh);

//			if (refresh != null) {
//				Users users = usersRepository.findByEmail(nowUser).orElseThrow(
//						() -> new RuntimeException("Can`t find Claim userEmail " + username));
//
//				NowUserDetails principalDetails = new NowUserDetails(users);
//
//				System.out.println(principalDetails.getAuthorities());
//
//				Authentication authentication =
//						new UsernamePasswordAuthenticationToken(
//								principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
//								null, // 패스워드는 null 처리, 어차피 지금 인증하는게 아니니까!!
//								principalDetails.getAuthorities());
//
//				// 강제로 시큐리티의 세션에 접근하여 값 저장
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}

//		}

			filterChain.doFilter(request, response);
	}
	
}
