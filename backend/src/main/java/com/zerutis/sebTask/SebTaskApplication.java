package com.zerutis.sebTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class SebTaskApplication {

	public static void main(String[] args) {
			SpringApplication.run(SebTaskApplication.class, args);
	}

	@Autowired
	XmlHandler xmlHandler;

	//@Scheduled(cron = "0 0 0 */1 * *")
	@Bean
	CommandLineRunner fillList() {
		return args -> {
			xmlHandler.fillCurrency();
		};
	}
}
