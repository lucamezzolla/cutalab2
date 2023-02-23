package com.cutalab.cutalab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Cutalab2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cutalab2Application.class, args);
	}

}