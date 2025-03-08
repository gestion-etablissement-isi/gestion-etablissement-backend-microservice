package com.isi.institution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmploiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmploiApplication.class, args);
	}

}
