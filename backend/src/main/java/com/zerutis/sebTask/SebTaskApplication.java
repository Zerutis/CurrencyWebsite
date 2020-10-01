package com.zerutis.sebTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SebTaskApplication {

	public static void main(String[] args) {
			SpringApplication.run(SebTaskApplication.class, args);
	}

	@Autowired
	XmlHandler xmlHandler;

	@Bean
	CommandLineRunner fillList() {
		return args -> {
			xmlHandler.fillCurrency();
		};
	}
}
