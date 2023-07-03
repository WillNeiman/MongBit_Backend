package com.MongMoong.MongBitProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MongBitProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongBitProjectApplication.class, args);
	}

}
