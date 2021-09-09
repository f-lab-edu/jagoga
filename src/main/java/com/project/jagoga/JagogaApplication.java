package com.project.jagoga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JagogaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JagogaApplication.class, args);
	}

}
