package com.example.demoactivitylogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoActivityLoggerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoActivityLoggerApplication.class, args);
	}
}
