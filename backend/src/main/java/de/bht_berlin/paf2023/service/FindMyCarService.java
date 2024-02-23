package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.repo.InsuranceRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMyCarService {
    private final VehicleRepo vehicleRepo;

    @Autowired
    public FindMyCarService(VehicleRepo repository) {
        this.vehicleRepo = repository;
    }

    public Insurance getParkingLocationByCar(Long id) {
        return null;
    }

    public Insurance getParkingLocationsByPerson(Long id) {
        return null;
    }

}
