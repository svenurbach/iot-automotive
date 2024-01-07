package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Paf2023Application {

	@Autowired
	private InsuranceService iService;

	public static void main(String[] args) {

		SpringApplication.run(Paf2023Application.class, args);

	}

//	@Override
	public void run(String[] args) throws Exception {
		// Hier können Sie die Anzahl der Dummy-Datensätze angeben
		iService.generateDummyData(10);
	}
}
