package com.openclassroom.ChaTop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ChaTopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaTopApplication.class, args);
	}

}
