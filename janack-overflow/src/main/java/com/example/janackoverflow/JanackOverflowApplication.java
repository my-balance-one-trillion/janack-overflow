package com.example.janackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories
public class JanackOverflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(JanackOverflowApplication.class, args);
	}
}
