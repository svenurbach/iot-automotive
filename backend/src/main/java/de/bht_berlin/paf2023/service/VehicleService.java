package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final VehicleModelRepo vehicleModelRepo;
    private final MeasurementRepo measurementRepo;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo, VehicleModelRepo vehicleModelRepo, MeasurementRepo measurementRepo) {
        this.vehicleRepo = vehicleRepo;
        this.vehicleModelRepo = vehicleModelRepo;
        this.measurementRepo = measurementRepo;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    public VehicleModel getVehicleModel(Long id) {
        return vehicleModelRepo.getById(id);
    }

    public Optional<Vehicle> getVehicleDetail(Long id) {
        return vehicleRepo.findById(id);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    public List<Measurement> findAllMeasurementsFromVehicleWithError(long vehicleId) {
        List<Measurement> test = measurementRepo.findAllMeasurementsFromVehicleWithError(vehicleId);
        System.out.println("test: " + test.size());
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i).getIsError() == true);
        }
        return test;
    }

    /**
     * Retrieves a list of vehicles associated with a specific policyholder.
     *
     * @param personId The ID of the policyholder.
     * @return A list of vehicles associated with the specified policyholder.
     */
    public List<Vehicle> getVehiclesByPerson(Long personId) {
        return vehicleRepo.findByInsuranceContract_Policyholder(personId);
    }

    /**
     * Retrieves the vehicle associated with a specific insurance contract.
     *
     * @param insuranceId The ID of the insurance contract.
     * @return The vehicle associated with the specified insurance contract.
     */
    public Vehicle getCarByIncurance(Long insuranceId) {
        return vehicleRepo.findByInsuranceContract(insuranceId);
    }

    public Trip findLastTripOfVehicle(long vehicleId) {
        return measurementRepo.findLastTripByVehicleId(vehicleId);
    }
}
