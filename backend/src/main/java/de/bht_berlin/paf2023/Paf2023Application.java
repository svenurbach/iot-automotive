package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.service.FakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

	@Autowired
	private FakerService iService;

	public static void main(String[] args) {

		SpringApplication.run(Paf2023Application.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		iService.generateDummyData("trip", 10);
//		iService.generateDummyData("insurance", 10);
//		iService.generateDummyData("person", 10);
//		iService.generateDummyData("measurement", 10);
//		iService.generateDummyData("vehicle", 10); //
	}
}
