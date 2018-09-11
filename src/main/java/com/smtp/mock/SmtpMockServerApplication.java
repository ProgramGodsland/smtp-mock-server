package com.smtp.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmtpMockServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmtpMockServerApplication.class, args);
	}
}
