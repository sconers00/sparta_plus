package com.example.plusteamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlusTeamProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlusTeamProjectApplication.class, args);
	}

}
