package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Insurance;
//import de.bht_berlin.paf2023.entity.measurements.EndLocationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.service.FindMyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/findMyCar")
public class FindMyCarRestController {

    @Autowired
    private FindMyCarService findMyCarService;

    @RequestMapping(path = "/byCar/{id}")
    public LocationMeasurement getLastPositionByCar(@PathVariable Long id) {
        return findMyCarService.getLastPositionByCar(id);
//        http://localhost:8080/api/findMyCar/byCar/1
    }

    @RequestMapping(path = "/byPerson/{id}")
    public List<LocationMeasurement> getLastPositionsByPerson(@PathVariable Long id) {
        return findMyCarService.getLastPositionsByPerson(id);
//        http://localhost:8080/api/findMyCar/byPerson/1
    }


}
