package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepo tripRepo;

    // Setter for TripService (for testing purposes)
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    // Setter for TripRepo (for testing purposes)
    public void setTripRepo(TripRepo tripRepo) {
        this.tripRepo = tripRepo;
    }

    @GetMapping(path = "/findAll")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping(path = "/{id}")
    public Optional<Trip> getTrip(@PathVariable Long id) {
        return tripRepo.findById(id);
    }

    @GetMapping(path = "/{id}/average-speed")
    public Double getAverageSpeed(@PathVariable Long id) {
        return tripService.getAverageSpeedForTrip(tripRepo.findById(id).get());
    }

    @GetMapping(path = "/{id}/total-distance")
    public Double getTotalDistanceForTrip(@PathVariable Long id) {
        return tripService.getTotalDistanceForTrip(tripRepo.findById(id).get());
    }

    /**
     * endpoint for getting all trips belonging to a list of vehicles passed as url params within a time range
     *
     * @param vehicleIds vehicle id
     * @param startTime  start time – date format example 2018-01-17T05:01:33.000Z
     * @param endTime    end time – date format example 2018-01-17T05:01:33.000Z
     * @return list of trips
     */
    @GetMapping(path = "/findAllByVehicleIds")
    public List<Trip> findAllByVehicleIds(@RequestParam List<Long> vehicleIds,
                                          @RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime) {
        if (startTime != null && endTime != null) {
            Date start = Date.from(Instant.parse(startTime));
            Date end = Date.from(Instant.parse(endTime));
            return tripService.findAllByVehicleIdsAndDateRange(vehicleIds, start, end);
        } else {
            return tripService.findAllByVehicleIds(vehicleIds);
        }
    }

    @GetMapping(path = "/findVehiclebyTripId/{id}")
    public Optional<Vehicle> findVehicleByTripId(@PathVariable Long id) {
        return tripService.findVehicleByTripId(id);
    }

    @GetMapping(path = "/findLastTripOfVehicle/{id}")
    public Trip findLastTripOfVehicle(@PathVariable Long id) {
        return tripService.findLastTripOfVehicle(id);
    }
}

