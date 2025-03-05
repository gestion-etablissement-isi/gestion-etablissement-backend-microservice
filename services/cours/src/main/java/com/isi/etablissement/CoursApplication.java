package com.isi.etablissement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursApplication.class, args);
	}

}
