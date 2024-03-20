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

    @Test
    void findErrorInPastArray_NoError() {
        MeasurementService measurementService = new MeasurementService(measurementRepo);
        int counter = 4;
        ArrayList<Measurement> values = new ArrayList<>();
        ArrayList<Double> measurementArrayInDouble = new ArrayList<>();
        Double tolerance = 0.1;
        int comparativeValuesArraySize = 3;
        // Testdata without errors
        for (int i = 0; i < 5; i++) {
            Measurement measurement = new Measurement();
            measurement.setIsError(false);
            values.add(measurement);
            measurementArrayInDouble.add(0.5);
        }
        boolean result = measurementService.findErrorInPastArray(counter, values, measurementArrayInDouble, tolerance, comparativeValuesArraySize);
        assertFalse(result, "Expected no error");
    }

    @Test
    void findErrorInPastArray_Error() {
        MeasurementService measurementService = new MeasurementService(measurementRepo);
        int counter = 5;
        ArrayList<Measurement> values = new ArrayList<>();
        ArrayList<Double> measurementArrayInDouble = new ArrayList<>();
        Double tolerance = 0.1;
        int comparativeValuesArraySize = 3;
        // Testdata with error
        for (int i = 0; i < 5; i++) {
            Measurement measurement = new Measurement();
            measurement.setIsError(false);
            values.add(measurement);
            measurementArrayInDouble.add(0.1);
        }
        Measurement measurement = new Measurement();
        values.add(measurement);
        measurementArrayInDouble.add(500.0);
        boolean result = measurementService.findErrorInPastArray(counter, values, measurementArrayInDouble, tolerance, comparativeValuesArraySize);
        assertTrue(result, "Expected an error");
    }
    }


