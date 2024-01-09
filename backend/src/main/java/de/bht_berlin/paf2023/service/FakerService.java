package de.bht_berlin.paf2023.service;

import com.github.javafaker.DateAndTime;
import de.bht_berlin.paf2023.entity.*;
import de.bht_berlin.paf2023.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import static java.util.concurrent.TimeUnit.DAYS;

@Service
public class FakerService {

    private final InsuranceRepo insuranceRepo;
    private final TripRepo tripRepo;
    private final AnalysisRepo analysisRepo;
    private final ContractRepo contractRepo;
    private final MeasurementRepo measurementRepo;
    private final PersonRepo personRepo;
    private final VehicleRepo vehicleRepo;
    private final Faker faker;

    @Autowired
    public FakerService(InsuranceRepo insuranceRepo, TripRepo tripRepo, AnalysisRepo analysisRepo, ContractRepo contractRepo, MeasurementRepo measurementRepo, PersonRepo personRepo, VehicleRepo vehicleRepo) {
        this.insuranceRepo = insuranceRepo;
        this.tripRepo = tripRepo;
        this.analysisRepo = analysisRepo;
        this.contractRepo = contractRepo;
        this.measurementRepo = measurementRepo;
        this.personRepo = personRepo;
        this.vehicleRepo = vehicleRepo;
        this.faker = new Faker(Locale.getDefault());
    }


//    public Date convertFakerDate(DateAndTime randomDate){
//        System.out.println("Date: " + randomDate);
//        String randomDateString = randomDate.toString();
//        System.out.println("randomDateString: " + randomDateString);
//        SimpleDateFormat sdf = new SimpleDateFormat("YYYY MM.dd mm:hh:ss", Locale.getDefault());
//        System.out.println("sdf: " + sdf);
//
//        Date javaUtilDate = null;
//
//        try {
//            javaUtilDate = sdf.parse(randomDateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return javaUtilDate;
//    }
    public void generateDummyData(String repoName, int numberOfEntries) {
        switch (repoName){
            case "insurance":
                for (int i = 0; i < numberOfEntries; i++) {
                    Insurance insurance = new Insurance();
                    insurance.setName(faker.artist().name());
                    this.insuranceRepo.save(insurance);
                }
                break;
            case "trip":
                for (int i = 0; i < numberOfEntries; i++) {
                Trip trip = new Trip();
                long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                Date javaUtilDate = new Date(randomTimestamp);

                trip.setTrip_start(javaUtilDate);

                trip.setTrip_end(faker.date().future(1, java.util.concurrent.TimeUnit.DAYS, javaUtilDate));
                trip.setAverage_speed((long) faker.number().numberBetween(10, 200));

                    this.tripRepo.save(trip);
            }
                break;
            case "analysis": for (int i = 0; i < numberOfEntries; i++) {
                Analysis analysis = new Analysis();
//                analysis.setName(faker.name().fullName());
                this.analysisRepo.save(analysis);
            }
                break;
            case "contract": for (int i = 0; i < numberOfEntries; i++) {
                Contract contract = new Contract();
//                contract.setInsuranceType(faker.);
                this.contractRepo.save(contract);
            }
                break;
            case "measurement": for (int i = 0; i < numberOfEntries; i++) {
                Measurement measurement = new Measurement();
                long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();

                Date javaUtilDate = new Date(randomTimestamp);
                measurement.setTimestamp(javaUtilDate);
                measurement.setInterval(faker.number().numberBetween(100,999));
                this.measurementRepo.save(measurement);
            }
                break;
            case "person": for (int i = 0; i < numberOfEntries; i++) {
                Person person = new Person();
                person.setName(faker.name().fullName());
//                person.setDriver(faker.random().nextBoolean());
//                person.setOwner(faker.random().nextBoolean());
                person.setCurrentTripID(faker.number().numberBetween(0L, 9999L));
                this.personRepo.save(person);
            }
                break;
            case "vehicle": for (int i = 0; i < numberOfEntries; i++) {
                Vehicle vehicle = new Vehicle();
//                vehicle.setVehicleModel(faker.number().numberBetween(100,999));
                vehicle.setLicensePlate(String.valueOf(faker.lorem().word()));
                this.vehicleRepo.save(vehicle);
            }
                break;
        }

    }
}