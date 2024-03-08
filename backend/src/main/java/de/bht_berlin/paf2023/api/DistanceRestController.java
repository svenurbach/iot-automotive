package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/trip")
public class DistanceRestController {
//
//    @Autowired
//    private DistanceService distanceService;
//
//    @RequestMapping(path = "/find/{id}")
//    public String getTrip(@PathVariable Long id) {
//        return distanceService.getTrip(id);
////        http://localhost:8080/api/trip/find/22
//    }
//
//    @GetMapping(path = "/findAll")
//    public String getAllTrips() {
//        return distanceService.getAllTrips();
////        http://localhost:8080/api/trip/findAll
//    }
}
