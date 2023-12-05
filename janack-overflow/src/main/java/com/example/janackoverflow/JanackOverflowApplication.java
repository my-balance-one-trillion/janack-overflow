package com.example.janackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JanackOverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanackOverflowApplication.class, args);
	}

}
