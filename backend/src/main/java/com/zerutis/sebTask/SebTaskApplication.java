package com.zerutis.sebTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SebTaskApplication {

	public static void main(String[] args) {
			SpringApplication.run(SebTaskApplication.class, args);
	}

	@Autowired
	XmlHandler xmlHandler;

	@Bean
	CommandLineRunner initPrintOneLine() {
		return args -> {
			xmlHandler.fillCurrency();
		};
	}
}
