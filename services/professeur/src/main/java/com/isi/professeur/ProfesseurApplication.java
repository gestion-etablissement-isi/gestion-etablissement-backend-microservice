package com.isi.professeur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProfesseurApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfesseurApplication.class, args);
	}

}
