package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.*;
import de.bht_berlin.paf2023.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

import java.util.*;

import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;

@Service
public class FakerService {

    private final InsuranceRepo insuranceRepo;
    private final TripRepo tripRepo;
    private final AnalysisRepo analysisRepo;
    private final ContractRepo contractRepo;
    private final MeasurementRepo measurementRepo;
    private final PersonRepo personRepo;
    private final VehicleRepo vehicleRepo;
    private final VehicleModelRepo vehicleModelRepo;
    private final Faker faker;

    @Autowired
    public FakerService(InsuranceRepo insuranceRepo, TripRepo tripRepo, AnalysisRepo analysisRepo, ContractRepo contractRepo, MeasurementRepo measurementRepo, PersonRepo personRepo, VehicleRepo vehicleRepo, VehicleModelRepo vehicleModelRepo) {
        this.insuranceRepo = insuranceRepo;
        this.tripRepo = tripRepo;
        this.analysisRepo = analysisRepo;
        this.contractRepo = contractRepo;
        this.measurementRepo = measurementRepo;
        this.personRepo = personRepo;
        this.vehicleRepo = vehicleRepo;
        this.vehicleModelRepo = vehicleModelRepo;
        this.faker = new Faker(Locale.getDefault());
    }


    public void generateDummyDataSet(Map dataSet) {
        System.out.println(dataSet);
        for (Object entity : dataSet.keySet()) {
            generateDummyData((String) entity, (Long) dataSet.get(entity), dataSet);
        }

    }

    public Long generateRandomForeignKey(Map dataSet, String key) {
        Long maxEntities = (Long) dataSet.get(key);
        if (maxEntities > 0) {
            return faker.number().numberBetween(1, maxEntities);
        }
        return 0L;
    }

    public void generateDummyData(String repoName, Long numberOfEntries, Map dataSet) {
        switch (repoName) {
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
                    trip.setAverage_speed(faker.number().numberBetween(0L, 140L));

//                    foreign key generation for person
                    Person existingPerson = this.personRepo.getById(generateRandomForeignKey(dataSet, "person"));
                    trip.setPerson(existingPerson);
                    
//                    foreign key generation for vehicle
                    Vehicle existingVehicle = this.vehicleRepo.getById(generateRandomForeignKey(dataSet, "vehicle"));
                    trip.setVehicle(existingVehicle);

                    this.tripRepo.save(trip);
                }
                break;
            case "analysis":
                for (int i = 0; i < numberOfEntries; i++) {
                    Analysis analysis = new Analysis();
                    this.analysisRepo.save(analysis);
                }
                break;
            case "contract":
                for (int i = 0; i < numberOfEntries; i++) {
                    Contract contract = new Contract();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    contract.setBegin(javaUtilDate);

                    contract.setContract_distance(faker.number().numberBetween(0L, 200000));

                    //                    foreign key generation for person
                    Insurance existingInsurance = this.insuranceRepo.getById(generateRandomForeignKey(dataSet,
                            "insurance"));
                    contract.setInsurance(existingInsurance);

                    //                    foreign key generation for person
                    Person existingPerson = this.personRepo.getById(generateRandomForeignKey(dataSet, "person"));
                    contract.setPolicyholderID(existingPerson);

                    //                    foreign key generation for vehicle
                    Vehicle existingVehicle = this.vehicleRepo.getById(generateRandomForeignKey(dataSet, "vehicle"));
                    contract.setVehicleID(existingVehicle);

                    this.contractRepo.save(contract);
                }
                break;
            case "measurement":
                for (int i = 0; i < numberOfEntries; i++) {
                    Measurement measurement = new Measurement();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();

                    Date javaUtilDate = new Date(randomTimestamp);
                    measurement.setTimestamp(javaUtilDate);
                    measurement.setInterval(faker.number().numberBetween(100, 999));
                    this.measurementRepo.save(measurement);
                }
                break;
            case "person":
                for (int i = 0; i < numberOfEntries; i++) {
                    Person person = new Person();
                    person.setName(faker.name().fullName());
//                person.setDriver(faker.random().nextBoolean());
//                person.setOwner(faker.random().nextBoolean());
                    person.setCurrentTripID(faker.number().numberBetween(0L, 9999L));
                    this.personRepo.save(person);
                }
                break;
            case "vehicle":
                for (int i = 0; i < numberOfEntries; i++) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setYearOfConstruction(faker.number().numberBetween(2000, 2023));
                    vehicle.setLicensePlate(String.valueOf(faker.lorem().word()));
                    //                    foreign key generation for vehicle
                    VehicleModel existingVehicleModel = this.vehicleModelRepo.getById(generateRandomForeignKey(dataSet,
                            "vehicle_model"));
                    vehicle.setVehicleModel(existingVehicleModel);

                    //                    foreign key generation for person
                    Person existingPerson = this.personRepo.getById(generateRandomForeignKey(dataSet, "person"));
                    vehicle.setPerson(existingPerson);

                    this.vehicleRepo.save(vehicle);
                }
                break;
            case "vehicle_model":
                for (int i = 0; i < numberOfEntries; i++) {
                    VehicleModel vehicleModel = new VehicleModel();
                    vehicleModel.setName(faker.funnyName().name());
                    this.vehicleModelRepo.save(vehicleModel);
                }
                break;
        }

    }
}