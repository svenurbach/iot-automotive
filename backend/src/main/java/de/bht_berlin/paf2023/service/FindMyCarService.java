package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;
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

    public EndLocationMeasurement getLastPositionByCar(Long id) {
        return vehicleRepo.findLastPositionByCarId(id);
    }

    public List<EndLocationMeasurement> getLastPositionsByPerson(Long id) {
        return vehicleRepo.findLastPositionByPersonId(id);
    }

}
