package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.FindMyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/findMyCar")
public class FindMyCarRestController {

    private final FindMyCarService findMyCarService;

    @Autowired
    public FindMyCarRestController(FindMyCarService findMyCarService) {
        this.findMyCarService = findMyCarService;
    }

    @GetMapping(path = "/{id}")
    // http://localhost:8080/api/findMyCar/1
    public List<Float> getLastCarPosition(@PathVariable Long id) {
        return findMyCarService.getLastCarPosition(id);
    }

}
