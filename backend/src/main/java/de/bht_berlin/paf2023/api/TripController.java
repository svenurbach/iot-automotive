package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping(path = "/findAll")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
//        http://localhost:8080/api/trips/findAll
    }

//    @GetMapping(path = "/findAll/{vehicleId}")
//    public List<Trip> getAllTripsByVehicle(@PathVariable Long vehicleId) {
//        return tripService.getAllTripsByVehicle(vehicleId);
//    }

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

    @GetMapping(path = "/findAll/{vehicleId}")
    public List<Trip> findAllByVehicleId(@PathVariable Long vehicleId,
                                         @RequestParam(required = false) String startTime,
                                         @RequestParam(required = false) String endTime) {
        if (startTime != null && endTime != null) {
            // date format example 2018-01-17T05:01:33.000Z
            Date start = Date.from(Instant.parse(startTime));
            Date end = Date.from(Instant.parse(endTime));
            return tripService.findAllByVehicleId(vehicleId, start, end);
        } else {
            return tripService.findAllByVehicleId(vehicleId);
        }
    }

    @GetMapping(path = "/findAllByVehicleIds")
    public List<Trip> findAllByVehicleIds(@RequestParam List<Long> vehicleIds,
                                          @RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime) {
        if (startTime != null && endTime != null) {
            // date format example 2018-01-17T05:01:33.000Z
            Date start = Date.from(Instant.parse(startTime));
            Date end = Date.from(Instant.parse(endTime));
            return tripService.findAllByVehicleIdsAndDateRange(vehicleIds, start, end);
        } else {
            return tripService.findAllByVehicleIds(vehicleIds);
        }
    }

}

