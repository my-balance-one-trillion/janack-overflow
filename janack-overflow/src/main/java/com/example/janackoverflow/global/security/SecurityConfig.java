package com.example.janackoverflow.global.security;

import com.example.janackoverflow.global.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

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

        http.csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                //'USER' 역할 사용자가 '/mypage' URL 패턴에 해당하는 요청 권한을 가진다
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/mypage", "/mypage/**").hasRole("USER")
                )
                //'ADMIN' 역할 사용자가 해당 URL 패턴에 해당하는 요청 권한을 가진다
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/mypage", "/mypage/**", "/admin", "/admin/**").hasRole("ADMIN")
                )
                // 모든 유저에게 해당 URL 패턴 개방 (화이트 리스트)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/image/**", "/css/**", "/", "/signup", "/community", "/community/**", "/saving").permitAll()
                )
                //폼 기반 로그인 구성
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login") // '/login' 경로를 사용자 정의 로그인 페이지로 지정
                                .loginProcessingUrl("/login") // submit 받을 url
                                .defaultSuccessUrl("/", true) //로그인 성공 시 기본으로 이동하는 경로
                                .permitAll() //모든 사용자가 로그인 페이지에 접근 가능
                )
                .logout(withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManagerBean());
    }

}
