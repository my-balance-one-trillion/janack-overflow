package com.example.janackoverflow.global.security;

import com.example.janackoverflow.global.security.jwt.JwtAuthenticationFilter;
import com.example.janackoverflow.global.security.jwt.JwtAuthorizationFilter;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

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

        // Spring Security에서 session을 생성하거나 사용하지 않도록 설정
        http.sessionManagement(httpSecuritySessionManagementConfigurer
                -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // jwt Filter 적용
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        //'USER' 역할 사용자가 '/mypage' URL 패턴에 해당하는 요청 권한을 가진다
        http.authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/image/**", "/css/**", "/", "/login", "/logout", "/signup", "/community/**", "/saving/**").permitAll() //화이트리스트
                                .requestMatchers("/mypage", "/mypage/**").hasRole("USER") //USER만 접근 가능
                                .requestMatchers("/mypage", "/mypage/**", "/admin", "/admin/**").hasRole("ADMIN") //ADMIN만 접근 가능
                                .anyRequest().authenticated()
                                //위에 작성된 url을 제외한 나머지는 인증절차 필요 (403 발생)
                ).exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/login"));
                                //권한이 없는 사용자를 '/login' 으로 이동

                //폼 기반 로그인 구성 <- 이거 구현하면 REST api 요청 막혀서 주석처리함
//                .formLogin((formLogin) ->
//                        formLogin
//                                .loginPage("/login") // '/login' 경로를 사용자 정의 로그인 페이지로 지정
//                                .loginProcessingUrl("/login") // submit 받을 url
//                                .defaultSuccessUrl("/", true) //로그인 성공 시 기본으로 이동하는 경로
//                                .permitAll() //모든 사용자가 로그인 페이지에 접근 가능
//                )
//                .logout(withDefaults());

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
