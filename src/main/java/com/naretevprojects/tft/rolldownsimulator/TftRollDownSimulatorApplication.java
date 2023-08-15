package com.naretevprojects.tft.rolldownsimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TftRollDownSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TftRollDownSimulatorApplication.class, args);
	}

}
