package com.edusmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.edusmart")
public class EduSmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduSmartApplication.class, args);
		System.out.println("hello");
	}

}
