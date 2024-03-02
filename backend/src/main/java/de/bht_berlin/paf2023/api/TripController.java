package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
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

    @GetMapping(path = "/{id}")
    public Optional<Trip> getTrip(@PathVariable Long id) {
        return tripRepo.findById(id);
    }
}

