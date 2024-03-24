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

    /**
     * generate fake for a given map with different set of entity types
     *
     * @param dataSet map containing entity types and amount of entities to be created
     */
    public void generateDummyDataSet(Map dataSet) {
        for (Object entity : dataSet.keySet()) {
            generateDummyData((String) entity, (Long) dataSet.get(entity), dataSet);
        }

    }

    /**
     * generates random ids of existing entities for foreign key references
     *
     * @param dataSet map of data set to be faked
     * @param key     entity type to get a foreign key of
     * @return foreign key as id
     */
    public Long generateRandomForeignKey(Map dataSet, String key) {
        Long maxEntities = (Long) dataSet.get(key);
        if (maxEntities > 0) {
            return faker.number().numberBetween(1, maxEntities);
        }
        return 0L;
    }

    /**
     * create entities for given map data set
     *
     * @param repoName        name of the repo the entity should be saved in
     * @param numberOfEntries amount of entities which should be created
     * @param dataSet         map of data set
     */
    public void generateDummyData(String repoName, Long numberOfEntries, Map dataSet) {
        switch (repoName) {
            case "insurance":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Insurance insurance = new Insurance();
                    insurance.setInsuranceName(faker.artist().name());
                    insurance.setInsuranceType(faker.company().profession());

                    // reference related entity by foreign key
                    InsuranceCompany existingCompany = this.insuranceCompanyRepo.getById(generateRandomForeignKey(dataSet, "insurance_company"));
                    insurance.setInsuranceCompany(existingCompany);

                    // save to repo
                    this.insuranceRepo.save(insurance);
                }
                break;
            case "insurance_company":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    InsuranceCompany insuranceCompany = new InsuranceCompany();
                    insuranceCompany.setCompanyName(faker.company().name());

                    // save to repo
                    this.insuranceCompanyRepo.save(insuranceCompany);
                }
                break;
            case "trip":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Trip trip = new Trip();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    trip.setTrip_start(javaUtilDate);
                    trip.setTrip_end(faker.date().future(1, java.util.concurrent.TimeUnit.DAYS, javaUtilDate));

                    // reference related entity by foreign key
                    Vehicle existingVehicle = this.vehicleRepo.getById(generateRandomForeignKey(dataSet, "vehicle"));

                    // save to repo
                    this.tripRepo.save(trip);
                }
                break;
            case "contract":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    InsuranceContract insuranceContract = new InsuranceContract();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    insuranceContract.setBegin(javaUtilDate);
                    insuranceContract.setContractDistance(faker.number().numberBetween(0L, 200000));
                    insuranceContract.setContractPrice((float) faker.number().randomDouble(2, 300, 1200));
                    insuranceContract.setPolicyNumber(faker.idNumber().valid());
                    insuranceContract.setDeductible(faker.number().numberBetween(0L, 1000));

                    // reference related entity by foreign key
                    Insurance existingInsurance = this.insuranceRepo.getById(generateRandomForeignKey(dataSet,
                            "insurance"));
                    insuranceContract.setInsurance(existingInsurance);

                    // reference related entity by foreign key
                    Person existingPerson = this.personRepo.getById(generateRandomForeignKey(dataSet, "person"));
                    insuranceContract.setPolicyholder(existingPerson);

                    // reference related entity by foreign key
//                    Vehicle existingVehicle = this.vehicleRepo.getById((long) i);
//                    insuranceContract.setVehicle(existingVehicle);

                    // save to repo
                    this.contractRepo.save(insuranceContract);
                }
                break;
            case "measurement":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Measurement measurement = new Measurement();
                    long randomTimestamp = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).getTime();
                    Date javaUtilDate = new Date(randomTimestamp);
                    measurement.setTimestamp(javaUtilDate);

                    // save to repo
                    this.measurementRepo.save(measurement);
                }
                break;
            case "person":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Person person = new Person();
                    String name = faker.name().firstName() + " " + faker.name().lastName();
                    person.setName(name);
                    person.setDateOfBirth(faker.date().birthday());

                    // save to repo
                    this.personRepo.save(person);
                }
                break;
            case "vehicle":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Vehicle vehicle = new Vehicle();
//                    vehicle.setYearOfConstruction(faker.number().numberBetween(2000, 2023));
                    vehicle.setLicensePlate(String.valueOf(faker.lorem().word()));
                    vehicle.setVin(faker.idNumber().validSvSeSsn());

                    // reference related entity by foreign key
                    VehicleModel existingVehicleModel = this.vehicleModelRepo.getById(generateRandomForeignKey(dataSet,
                            "vehicle_model"));
                    vehicle.setVehicleModel(existingVehicleModel);

                    // save to repo
                    this.vehicleRepo.save(vehicle);
                }
                break;
            case "vehicle_model":
                // iterate through the amount of entities to be created
                for (int i = 0; i < numberOfEntries; i++) {
                    // create new entity and set mandatory attributes
                    Random random = new Random();
                    String[] imgURLArray = {"https://image.stern.de/8424922/t/8I/v2/w1440/r0/-/30--artikel22517bild01jpg---b5e7066e38d38876.jpg",
                            "https://apps-cloud.n-tv.de/img/14348811-1421670548000/16-9/750/Opel-Adam.jpg",
                            "https://www.sueddeutsche.de/image/sz.1.4713503/704x396?v=1575642916"};
                    int randomIndex = random.nextInt(imgURLArray.length);
                    VehicleModel vehicleModel = new VehicleModel();
                    vehicleModel.setModelName(faker.funnyName().name());
                    vehicleModel.setManufacturer(faker.name().username());
//                    vehicleModel.setWeight((float) faker.number().randomDouble(2, 300, 1200));
//                    vehicleModel.setFueltype();
                    vehicleModel.setMaxSpeed((float) faker.number().numberBetween(180, 600));
                    vehicleModel.setMaxAcceleration((float) faker.number().numberBetween(0, 100));
                    vehicleModel.setMaxAxis((float) faker.number().numberBetween(300, 360));
                    vehicleModel.setMinAxis((float) faker.number().numberBetween(0, 40));
                    vehicleModel.setMaxSteeringWheel((float) faker.number().numberBetween(300, 360));
                    vehicleModel.setMinSteeringWheel((float) faker.number().numberBetween(0, 20));
                    vehicleModel.setAccelerationTolerance((float) 0.8);
//                    vehicleModel.setAccelerationTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setSpeedTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setAxisTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setLocationTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setSteeringWheelTolerance((float) faker.number().randomDouble(2, 0, 1));
                    vehicleModel.setImgURL(imgURLArray[randomIndex]);

                    // save to repo
                    this.vehicleModelRepo.save(vehicleModel);
                }
                break;
        }
    }

    public void makePlausibleData() {
        List<Person> persons = personRepo.findAll();
        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepo.findAll();
        List<Insurance> insurances = insuranceRepo.findAll();
        List<VehicleModel> vehicleModels = vehicleModelRepo.findAll();
        List<Vehicle> vehicles = vehicleRepo.findAll();
        List<InsuranceContract> insuranceContracts = contractRepo.findAll();

        InsuranceCompany insuranceCompanyOne = insuranceCompanies.get(0);
        insuranceCompanyOne.setCompanyName("Alianz");
        InsuranceCompany insuranceCompanyTwo = insuranceCompanies.get(1);
        insuranceCompanyTwo.setCompanyName("HUK Coburg");
        for (int i = 0; i < insuranceCompanies.size(); i++) {
            insuranceCompanyRepo.save(insuranceCompanies.get(i));
        }

        Insurance insuranceOne = insurances.get(0);
        insuranceOne.setInsuranceCompany(insuranceCompanyTwo);
        insuranceOne.setInsuranceName("Classic Select");
        insuranceOne.setInsuranceType("Teilkasko");

        Insurance insuranceTwo = insurances.get(1);
        insuranceTwo.setInsuranceCompany(insuranceCompanyOne);
        insuranceTwo.setInsuranceName("Komfort");
        insuranceTwo.setInsuranceType("Vollkasko");

        Insurance insuranceThree = insurances.get(2);
        insuranceThree.setInsuranceCompany(insuranceCompanyOne);
        insuranceThree.setInsuranceName("Premium");
        insuranceThree.setInsuranceType("Vollkasko");


        for (int i = 0; i < insurances.size(); i++) {
            insuranceRepo.save(insurances.get(i));
        }

        VehicleModel vehicleModelOne = vehicleModels.get(0);
        vehicleModelOne.setManufacturer("Volkswagen");
        vehicleModelOne.setModelName("Golf GTE 1");
        vehicleModelOne.setImgURL("https://uploads.vw-mms.de/system/production/images/vwn/080/882/images/2e3b58f75abcbedd61fe45f93fd1283e148760b8/DB2024AU00138_web_1600.jpg");
        vehicleModelOne.setMaxSpeed(200.0f);
        vehicleModelOne.setMaxAcceleration(8.0f);
        vehicleModelOne.setMinAcceleration(-5.0f);
        vehicleModelOne.setMaxAxis(45.0f);
        vehicleModelOne.setMinAxis(-45.0f);
        vehicleModelOne.setMaxSteeringWheel(600.0f);
        vehicleModelOne.setMinSteeringWheel(-600.0f);
        vehicleModelOne.setSpeedTolerance(5.9f);
        vehicleModelOne.setAccelerationTolerance(5.0f);
        vehicleModelOne.setAxisTolerance(5.5f);
        vehicleModelOne.setSteeringWheelTolerance(5.0f);

        VehicleModel vehicleModelTwo = vehicleModels.get(1);
        vehicleModelTwo.setManufacturer("Volkswagen");
        vehicleModelTwo.setModelName("Tiguan Allspace");
        vehicleModelTwo.setImgURL("https://uploads.vw-mms.de/system/production/images/vwn/036/795/images/5e5fc9aa428b278b08eedc58b10ede408e569a88/DB2021AU00494_web_1600.jpg");
        vehicleModelTwo.setMaxSpeed(190.0f);
        vehicleModelTwo.setMaxAcceleration(8.5f);
        vehicleModelTwo.setMinAcceleration(-5.0f);
        vehicleModelTwo.setMaxAxis(40.0f);
        vehicleModelTwo.setMinAxis(-40.0f);
        vehicleModelTwo.setMaxSteeringWheel(700.0f);
        vehicleModelTwo.setMinSteeringWheel(-700.0f);
        vehicleModelTwo.setSpeedTolerance(5.9f);
        vehicleModelTwo.setAccelerationTolerance(5.0f);
        vehicleModelTwo.setAxisTolerance(5.5f);
        vehicleModelTwo.setSteeringWheelTolerance(5.0f);

        VehicleModel vehicleModelThree = vehicleModels.get(2);
        vehicleModelThree.setManufacturer("Peugot");
        vehicleModelThree.setModelName("308");
        vehicleModelThree.setImgURL("https://www.autozeitung.de/assets/styles/article_image/public/field/image/peugeot-308-sw-2021-01.jpg");
        vehicleModelThree.setMaxSpeed(190.0f);
        vehicleModelThree.setMaxAcceleration(9.0f);
        vehicleModelThree.setMinAcceleration(-5.0f);
        vehicleModelThree.setMaxAxis(38.0f);
        vehicleModelThree.setMinAxis(-38.0f);
        vehicleModelThree.setMaxSteeringWheel(750.0f);
        vehicleModelThree.setMinSteeringWheel(-750.0f);
        vehicleModelThree.setSpeedTolerance(2.9f);
        vehicleModelThree.setAccelerationTolerance(5.0f);
        vehicleModelThree.setAxisTolerance(5.5f);
        vehicleModelThree.setSteeringWheelTolerance(1.0f);

        for (int i = 0; i < vehicleModels.size(); i++) {
            vehicleModelRepo.save(vehicleModels.get(i));
        }

        Vehicle vehicleOne = vehicles.get(0);
        vehicleOne.setYearOfConstruction(2014);
        vehicleOne.setLicensePlate("B-AS-1467");
        vehicleOne.setVehicleModel(vehicleModelOne);

        Vehicle vehicleTwo = vehicles.get(1);
        vehicleTwo.setYearOfConstruction(2018);
        vehicleTwo.setLicensePlate("B-AS-2451");
        vehicleTwo.setVehicleModel(vehicleModelTwo);

        Vehicle vehicleThree = vehicles.get(2);
        vehicleThree.setYearOfConstruction(2022);
        vehicleThree.setLicensePlate("B-NE-3347");
        vehicleThree.setVehicleModel(vehicleModelThree);

        for (int i = 0; i < vehicles.size(); i++) {
            vehicleRepo.save(vehicles.get(i));
        }

        InsuranceContract insuranceContractOne = insuranceContracts.get(0);
        insuranceContractOne.setPolicyholder(persons.get(0));
        insuranceContractOne.setVehicle(vehicleOne);
        insuranceContractOne.setInsurance(insuranceOne);

        InsuranceContract insuranceContractTwo = insuranceContracts.get(1);
        insuranceContractTwo.setPolicyholder(persons.get(0));
        insuranceContractTwo.setVehicle(vehicleTwo);
        insuranceContractTwo.setInsurance(insuranceTwo);

        InsuranceContract insuranceContractThree = insuranceContracts.get(2);
        insuranceContractThree.setPolicyholder(persons.get(1));
        insuranceContractThree.setVehicle(vehicleThree);
        insuranceContractThree.setInsurance(insuranceThree);
        for (int i = 0; i < insuranceContracts.size(); i++) {
            contractRepo.save(insuranceContracts.get(i));
        }

    }
}