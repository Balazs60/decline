package com.github.balazs60.decline;

import com.github.balazs60.decline.generator.DataGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeclineApplication {

	private DataGenerator dataGenerator;

	public DeclineApplication(DataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}

	@PostConstruct
	public void seedDatabase() {
		dataGenerator.seedNouns();
		dataGenerator.seedAdjectives();
	}
	public static void main(String[] args) {
		SpringApplication.run(DeclineApplication.class, args);
	}

}
