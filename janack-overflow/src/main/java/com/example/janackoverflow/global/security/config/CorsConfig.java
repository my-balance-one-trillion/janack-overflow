package com.example.janackoverflow.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Cross 도메인 요청 시 인증 정보를 포함할지 여부 - true로 자격 증명 허용
        corsConfiguration.setAllowCredentials(true);

        //허용된 HTTP 헤더 설정
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("X-Requested-With");
        corsConfiguration.addAllowedHeader("Pragma");

        //허용된 오리진(출처) 도메인 설정
        corsConfiguration.addAllowedOrigin("http://localhost:5173");

        corsConfiguration.addAllowedOriginPattern("/**");

        //허용된 HTTP 메서드 설정
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS");

        //브라우저에 노출할 헤더를 설정 - 클라이언트에서 해당 헤더에 접근할 수 있도록 사용됨
        corsConfiguration.addExposedHeader("Authorization");

        //모든 URL 패턴 경로 '/**'에 위에 작성된 모든 corsConfiguration 정책을 지정함
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
