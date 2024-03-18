package de.bht_berlin.paf2023.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class MeasurementServiceTest {
    @Mock
    private MeasurementRepo measurementRepo;

}


//    @Test
//    public void testFindMeasurementError_WithError() {
//        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 20000.0, 15.0, 25.00, 27.00, 30.0, 15.0, 5.0));
////        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 10.0, 10.0, 10.0, 10.0, 10.0));
//        int comparativeValuesArraySize = 3;
//        Double tolerance = 0.1;
//        MeasurementService measurementService = new MeasurementService(measurementRepo);
//        boolean result = measurementService.findMeasurementError(measurementArrayInDouble, comparativeValuesArraySize, tolerance);
//        assertTrue(result);
//    }

