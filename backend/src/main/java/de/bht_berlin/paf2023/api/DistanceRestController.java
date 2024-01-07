package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/distance")
public class DistanceRestController {

    @Autowired
    private DistanceService distanceService;

    @RequestMapping(path = "/test/{id}")
    public String testApi(@PathVariable Long id) {
        return distanceService.testApi(id);
    }
}
