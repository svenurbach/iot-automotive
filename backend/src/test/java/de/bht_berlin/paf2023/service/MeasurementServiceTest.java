package de.bht_berlin.paf2023.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class MeasurementServiceTest {
    @Mock
    private MeasurementRepo measurementRepo;

    /**
     * This test method verifies the behavior of the findErrorInPastArray method in the MeasurementService class when
     * no errors are present. It creates a MeasurementService instance with a given measurementRepo and sets up test
     * data including measurements and corresponding values. The method to be tested is called with the test data,
     * and the result is asserted to ensure it returns false, indicating no error.
     */
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
        boolean result = measurementService.findErrorInPastArray(counter, values, measurementArrayInDouble, tolerance,
                comparativeValuesArraySize);
        assertFalse(result, "Expected no error");
    }

    /**
     * This test method verifies the behavior of the findErrorInPastArray method in the MeasurementService class when
     * an error is present. It creates a MeasurementService instance with a given measurementRepo and sets up test
     * data including measurements and corresponding values. The method to be tested is called with the test data,
     * and the result is asserted to ensure it returns true, indicating an error.
     */
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
        boolean result = measurementService.findErrorInPastArray(counter, values, measurementArrayInDouble, tolerance,
                comparativeValuesArraySize);
        assertTrue(result, "Expected an error");
    }
    }


