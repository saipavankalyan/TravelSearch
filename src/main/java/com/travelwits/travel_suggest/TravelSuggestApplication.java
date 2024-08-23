package com.travelwits.travel_suggest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class TravelSuggestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelSuggestApplication.class, args);
	}

}
