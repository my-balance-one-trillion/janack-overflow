package com.example.janackoverflow.global.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 허용된 Origin(출처) 설정
        config.addAllowedOrigin("*");

        // 허용된 HTTP 메서드 설정
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");  // CORS preflight 요청을 허용하려면 OPTIONS도 추가

        // 허용된 헤더 설정
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Cache-Control");
        config.addAllowedHeader("Content-Type");

        //클라이언트에서 해당 헤더에 접근할 수 있도록 허용
        config.addExposedHeader("Authorization");

        // 인증 정보를 포함할지 여부 - true로 자격증명 허용
        config.setAllowCredentials(true);

        //모든 URL에 대해 해당 설정 적용
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
