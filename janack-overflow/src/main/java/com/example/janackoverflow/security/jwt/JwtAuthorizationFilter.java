package com.example.janackoverflow.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.janackoverflow.security.auth.NowUserDetails;
import com.example.janackoverflow.user.entity.Users;
import com.example.janackoverflow.user.repository.UsersRepository;
import com.example.sugarroad2.config.auth.NowUserDetails;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	//요청 헤더의 아이디와 패스워드를 파싱하여 인증 요청을 위임하는 필터
	//인증 성공 여부에 따라 핸들러 실행
	
	private UsersRepository usersRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository userRepository) {
		super(authenticationManager);
		this.usersRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(JwtProperties.HEADER_STRING); //헤더 추출
		
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		System.out.println("필터 수행 - 현재 header : " + header);

		String token = header.replace(JwtProperties.TOKEN_PREFIX, "");

		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
				.build()
				.verify(token)
				.getClaim("email")
				.asString();

		System.out.println("인증 필터 수행 중인 유저 : " + username);

		if(username != null) {
			Users user = usersRepository.findByEmail(username).get();

			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			NowUserDetails principalDetails = new NowUserDetails(user);

			System.out.println(principalDetails.getAuthorities());

			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
							null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
							principalDetails.getAuthorities());

			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
	
}
