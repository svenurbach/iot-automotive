package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.service.FakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

    @Autowired
    private FakerService iService;

    public static void main(String[] args) {

        SpringApplication.run(Paf2023Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, Long> dataSet = new LinkedHashMap<String, Long>();
        dataSet.put("person", 10L);
        dataSet.put("insurance", 10L);
        dataSet.put("vehicle_model", 10L);
        dataSet.put("vehicle", 10L);
        dataSet.put("trip", 10L);
        dataSet.put("contract", 10L);

        iService.generateDummyDataSet(dataSet);
    }
}
