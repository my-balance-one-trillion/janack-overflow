package com.example.janackoverflow.global.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
	private String header;
	private String secret1;
	private String secret2;
	private Long tokenValidityInSeconds;
}
