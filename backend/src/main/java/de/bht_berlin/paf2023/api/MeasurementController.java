package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/measurement")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

}

