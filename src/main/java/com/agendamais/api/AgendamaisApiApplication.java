package com.agendamais.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@Validated
public class AgendamaisApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamaisApiApplication.class, args);
	}

}
