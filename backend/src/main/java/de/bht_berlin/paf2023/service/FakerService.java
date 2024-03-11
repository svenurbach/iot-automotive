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
    private final InsuranceCompanyRepo insuranceCompanyRepo;
    private final TripRepo tripRepo;
    private final ContractRepo contractRepo;
    private final MeasurementRepo measurementRepo;
    private final PersonRepo personRepo;
    private final VehicleRepo vehicleRepo;
    private final VehicleModelRepo vehicleModelRepo;
    private final Faker faker;

    @Autowired
    public FakerService(InsuranceRepo insuranceRepo, InsuranceCompanyRepo insuranceCompanyRepo, TripRepo tripRepo, ContractRepo contractRepo, MeasurementRepo measurementRepo, PersonRepo personRepo, VehicleRepo vehicleRepo, VehicleModelRepo vehicleModelRepo) {
        this.insuranceRepo = insuranceRepo;
        this.insuranceCompanyRepo = insuranceCompanyRepo;
        this.tripRepo = tripRepo;
        this.contractRepo = contractRepo;
        this.measurementRepo = measurementRepo;
        this.personRepo = personRepo;
        this.vehicleRepo = vehicleRepo;
        this.vehicleModelRepo = vehicleModelRepo;
        this.faker = new Faker(Locale.getDefault());
    }


    public void generateDummyDataSet(Map dataSet) {
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
                    insurance.setInsuranceName(faker.artist().name());
                    insurance.setInsuranceType(faker.company().profession());

                    // foreign key generation for person
                    InsuranceCompany existingCompany = this.insuranceCompanyRepo.getById(generateRandomForeignKey(dataSet, "insurance_company"));
                    insurance.setInsuranceCompany(existingCompany);

                    this.insuranceRepo.save(insurance);
                }
                break;
            case "insurance_company":
                for (int i = 0; i < numberOfEntries; i++) {
                    InsuranceCompany insuranceCompany = new InsuranceCompany();
                    insuranceCompany.setCompanyName(faker.company().name());
                    this.insuranceCompanyRepo.save(insuranceCompany);
                }
                break;
            case "trip":
                for (int i = 0; i < numberOfEntries; i++) {
                    Trip trip = new Trip();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    trip.setTrip_start(javaUtilDate);
                    trip.setTrip_end(faker.date().future(1, java.util.concurrent.TimeUnit.DAYS, javaUtilDate));
//                    trip.setAverage_speed(faker.number().numberBetween(0L, 140L));

//                    foreign key generation for vehicle
                    Vehicle existingVehicle = this.vehicleRepo.getById(generateRandomForeignKey(dataSet, "vehicle"));
//                    trip.setVehicle(existingVehicle);

                    this.tripRepo.save(trip);
                }
                break;
            case "contract":
                for (int i = 0; i < numberOfEntries; i++) {
                    InsuranceContract insuranceContract = new InsuranceContract();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    insuranceContract.setBegin(javaUtilDate);
                    insuranceContract.setContractDistance(faker.number().numberBetween(0L, 200000));
                    insuranceContract.setContractPrice((float) faker.number().randomDouble(2, 300, 1200));
                    insuranceContract.setPolicyNumber(faker.idNumber().valid());
                    insuranceContract.setDeductible(faker.number().numberBetween(0L, 1000));

                    //                    foreign key generation for person
                    Insurance existingInsurance = this.insuranceRepo.getById(generateRandomForeignKey(dataSet,
                            "insurance"));
                    insuranceContract.setInsurance(existingInsurance);

                    //                    foreign key generation for person
                    Person existingPerson = this.personRepo.getById(generateRandomForeignKey(dataSet, "person"));
                    insuranceContract.setPolicyholder(existingPerson);

                    //                    foreign key generation for vehicle
                    Vehicle existingVehicle = this.vehicleRepo.getById(generateRandomForeignKey(dataSet, "vehicle"));
                    insuranceContract.setVehicle(existingVehicle);

                    this.contractRepo.save(insuranceContract);
                }
                break;
            case "measurement":
                for (int i = 0; i < numberOfEntries; i++) {
                    Measurement measurement = new Measurement();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();

                    Date javaUtilDate = new Date(randomTimestamp);
                    measurement.setTimestamp(javaUtilDate);
//                    measurement.setInterval(faker.number().numberBetween(100, 999));
                    this.measurementRepo.save(measurement);
                }
                break;
            case "person":
                for (int i = 0; i < numberOfEntries; i++) {
                    Person person = new Person();
                    person.setName(faker.name().fullName());
                    person.setDateOfBirth(faker.date().birthday());
                    person.setCurrentTripID(faker.number().numberBetween(0L, 9999L));
                    this.personRepo.save(person);
                }
                break;
            case "vehicle":
                for (int i = 0; i < numberOfEntries; i++) {
                    Vehicle vehicle = new Vehicle();
//                    vehicle.setYearOfConstruction(faker.number().numberBetween(2000, 2023));
                    vehicle.setLicensePlate(String.valueOf(faker.lorem().word()));
                    vehicle.setVin(faker.idNumber().validSvSeSsn());
                    //                    foreign key generation for vehicle
                    VehicleModel existingVehicleModel = this.vehicleModelRepo.getById(generateRandomForeignKey(dataSet,
                            "vehicle_model"));
                    vehicle.setVehicleModel(existingVehicleModel);
                    this.vehicleRepo.save(vehicle);
                }
                break;
            case "vehicle_model":
                for (int i = 0; i < numberOfEntries; i++) {
                    VehicleModel vehicleModel = new VehicleModel();
                    vehicleModel.setModelName(faker.funnyName().name());
                    vehicleModel.setConstructionYear(faker.number().numberBetween(2000, 2023));
                    vehicleModel.setManufacturer(faker.name().username());
                    vehicleModel.setWeight((float) faker.number().randomDouble(2, 300, 1200));
//                    vehicleModel.setFueltype();
                    vehicleModel.setMaxSpeed((float) faker.number().numberBetween(180, 600));
                    vehicleModel.setMaxAcceleration((float) faker.number().numberBetween(-20, 100));
                    vehicleModel.setAccelerationTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setSpeedTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setFuelLevelTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setLocationTolerance((float) faker.number().randomDouble(2, 0, 1));
                    this.vehicleModelRepo.save(vehicleModel);
                }
                break;
        }

    }
}