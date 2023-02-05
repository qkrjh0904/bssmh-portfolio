package com.bssmh.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com")
public class BssmhPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BssmhPortfolioApplication.class, args);
	}

}
