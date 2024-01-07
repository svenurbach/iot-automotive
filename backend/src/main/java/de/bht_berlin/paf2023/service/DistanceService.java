package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Contract;
import de.bht_berlin.paf2023.entity.Trip;

import java.util.List;

public class DistanceService {


    public Long calculate_distance_per_trip(Trip trip){
        Long distance;
        Long time = trip.getTrip_end().getTime() - trip.getTrip_start().getTime();
        Long average_speed = trip.get_average_speed();
        distance = time * average_speed;
        return distance;
    }

    public Long calculateTotalDistanceForYear(List<Trip> trips, int year) {
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
        Long contract_distance = contract.get_contract_distance();
        Long diff = driven_distance / contract_distance * 100;
        deviation = (int) (diff - 100);
        return deviation;
    }

}
