package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/meassurement")
public class MeasurementController {

    // User Story: Messefehler/Inkonsistenzen erkennen
    // https://trello.com/c/1bafUTDM/37-user-story-messfehler-inkonsistenzen-erkennen

    @Autowired
    private MeasurementService measurementService;

//    @RequestMapping(path = "/errors/{errorId}")
//    public String getMeasurements(@PathVariable Long id) {
//        return measurementService.getMeasurements(id);
//    }


    // Get all measurements from trip
    // /measurement/{vehicle}?trip=x
    @RequestMapping(path = "/measurement/{vehicle}")
    public String getAllMeasurements(@PathVariable Long id){
        return "test";
    }

    // Get single measurements from trip
    // /measurement/{vehicle}?trip=x&metric=y

//    show if error in measurement exists for latest trip
    // /measurement/{vehicle}/error
}

