package com.nsp.cowsandbullss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.nsp.cowsandbullss")
@EntityScan(basePackages = "com.nsp.cowsandbullss")
@SpringBootApplication
public class CowsandbullssApplication {

	public static void main(String[] args) {
		SpringApplication.run(CowsandbullssApplication.class, args);
	}

}

