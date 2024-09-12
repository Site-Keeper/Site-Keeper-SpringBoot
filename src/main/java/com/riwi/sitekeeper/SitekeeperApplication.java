package com.riwi.sitekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SitekeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SitekeeperApplication.class, args);
	}

}
