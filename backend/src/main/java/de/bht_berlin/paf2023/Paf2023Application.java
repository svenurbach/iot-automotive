package de.bht_berlin.paf2023;

import de.bht_berlin.paf2023.api.MeasurementController;
import de.bht_berlin.paf2023.component.MeasurementControllerSingleton;
import de.bht_berlin.paf2023.component.SegmentTripsInDBStrategy;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.FakerService;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Array;
import java.util.*;

//@EnableScheduling
@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Paf2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
