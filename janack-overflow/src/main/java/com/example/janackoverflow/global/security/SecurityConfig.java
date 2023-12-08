package com.example.janackoverflow.global.security;

import com.example.janackoverflow.global.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //허용된 HTTP 헤더 설정
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        //허용된 오리진(출처)설정 - 임의로 모든 도메인 요청 허용 *
        corsConfiguration.setAllowedOrigins(List.of("*"));

        //허용된 HTTP 메서드 설정
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));

        //Cross 도메인 요청 시 인증 정보를 포함할지 여부 - true로 자격증명 허용
        corsConfiguration.setAllowCredentials(true);

        //브라우저에 노출할 헤더를 설정 - 클라이언트에서 해당 헤더에 접근할 수 있도록 사용됨
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        //모든 URL 패턴 경로 '/**'에 위에 작성된 모든 corsConfiguration 정책을 지정함
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        http.csrf().disable()
                //'USER' 역할 사용자가 '/mypage' URL 패턴에 해당하는 요청 권한을 가진다
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/mypage").hasRole("USER")
                )
                //'ADMIN' 역할 사용자가 해당 URL 패턴에 해당하는 요청 권한을 가진다
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/mypage", "/admin").hasRole("ADMIN")
                )
                // 모든 유저에게 해당 URL 패턴 개방 (화이트 리스트)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/**", "/", "/login", "/logout", "/signup", "/community", "/saving").permitAll()
                )
                //폼 기반 로그인 구성
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login") // '/login' 경로를 사용자 정의 로그인 페이지로 지정
                                .defaultSuccessUrl("/") //로그인 성공 시 기본으로 이동하는 경로
                                .permitAll() //모든 사용자가 로그인 페이지에 접근 가능
                );

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//
//        UserDetails user =
//                User.builder()
//                        .username("user") //사용자 이름
//                        .password(passwordEncoder.encode("password")) //패스워드 암호화
//                        .roles("USER") //역할(권한) 부여
//                        .build();
//
//        UserDetails admin = User.builder()
//                .username("admin") //관리자 이름
//                .password(passwordEncoder.encode("admin")) //관리자 패스워드
//                .roles("ADMIN") //역할(권한) 부여
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//        //InMemory ~ Manager : 메모리에 사용자 정보(userDetails)를 저장하고 관리하는 데 사용됨
//    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManagerBean());
    }

}
