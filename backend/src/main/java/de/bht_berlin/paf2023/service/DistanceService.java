package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Contract;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistanceService {
    private final TripRepo repository;

    @Autowired
    public DistanceService(TripRepo repository) {
        this.repository = repository;
    }

    public List<Trip> getAllTripsOfVehicleID(Long vehicleID) {
        return repository.findAll();
    }

    public Long calculate_distance_per_trip(Trip trip){
        Long distance;
        Long time = trip.getTrip_end().getTime() - trip.getTrip_start().getTime();
        Long average_speed = trip.get_average_speed();
        distance = time * average_speed;
        return distance;
    }

    public Long calculateTotalDistanceForYear(Long vehicleID, int year) {
        List<Trip> trips = getAllTripsOfVehicleID(vehicleID);
        Long totalDistance = 0L;

        for (Trip trip : trips) {
            if (isInYear(trip, year)) {
                Long distance = calculate_distance_per_trip(trip);
                if (distance != -1L) {
                    totalDistance += distance;
                }
            }
        }

        return totalDistance;
    }

    public boolean isInYear(Trip trip, int year) {
        int tripYear = trip.getTrip_start().getYear() + 2000;
        return tripYear == year;
    }

    public int compare_driven_distance_with_contract_distance(Long driven_distance, Contract contract){
        int deviation = 0;
        Long contract_distance = contract.getContractDistance();
        Long diff = driven_distance / contract_distance * 100;
        deviation = (int) (diff - 100);

        // example -10% off contract_distance
        return deviation;
    }

    public String getAllTrips(){
        return repository.findAll().toString();
    }

    public String getTrip(long id){
        return repository.findById(id).get().get_average_speed().toString();
//        return "test works";
//        return repository.findAllByVehicle(id).toString();

    }

}
