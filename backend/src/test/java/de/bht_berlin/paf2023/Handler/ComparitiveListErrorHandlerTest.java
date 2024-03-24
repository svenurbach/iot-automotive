package de.bht_berlin.paf2023.Handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.handler.ComparitiveListErrorHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ComparitiveListErrorHandlerTest {

    /**
     * This test method verifies the behavior of the checkForErrorsPublic method in the ComparitiveListErrorHandler class.
     * It sets up mocks for dependencies including MeasurementService, MeasurementRepoSubject, and ArrayLists.
     * Test data is prepared including measurements and a HashMap containing measurement lists grouped by type.
     * The method to be tested is invoked with test data and mocks are configured to return expected values.
     * The method result is asserted to ensure it meets the expected behavior.
     */
    @Test
    void checkForErrorsPublicTest() {
        // set Mocks for measurementService, measurementArrayInDouble and hashMap
        MeasurementService measurementService = mock(MeasurementService.class);
        MeasurementRepoSubject measurementRepoSubject = mock(MeasurementRepoSubject.class);
        ArrayList<Double> measurementArrayInDouble = mock(ArrayList.class);

        // set Mocks for Measurements
        Measurement measurement1 = mock(Measurement.class);
        Measurement measurement2 = mock(Measurement.class);
        Measurement measurement3 = mock(Measurement.class);
        Measurement measurement4 = mock(Measurement.class);
        Measurement measurement5 = mock(Measurement.class);

        HashMap<String, ArrayList<Measurement>> hashMap = new HashMap<>();

        // add measurements to ArrayList
        ArrayList<Measurement> measurementList = new ArrayList<>();
        measurementList.add(measurement1);
        measurementList.add(measurement2);
        measurementList.add(measurement3);
        measurementList.add(measurement4);
        measurementList.add(measurement5);
        hashMap.put("SpeedMeasurement", measurementList);

        ComparitiveListErrorHandler errorHandler = new ComparitiveListErrorHandler(measurementRepoSubject, measurementService);

        // set testdata
        int index = 4;
        String type = "SpeedMeasurement";
        double tolerance = 0.1;

        // set behaviour of methods which are called in tested method
        when(measurementService.findErrorInFutureArray(anyInt(), any(), any(), anyDouble(), anyInt())).thenReturn(true);
        when(measurementService.findErrorInPastArray(anyInt(), any(), any(), anyDouble(), anyInt())).thenReturn(false);

        // call method to be tested
        boolean result = errorHandler.checkForErrorsPublic(index, measurementArrayInDouble, hashMap, type, tolerance);

        assertTrue(result);
    }




}
