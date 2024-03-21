package de.bht_berlin.paf2023.Handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.handler.ComparitiveListErrorHandler;
import de.bht_berlin.paf2023.handler.MeasurementHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ComparitiveListErrorHandlerTest {
    @Test
    void testCheckForErrorsPublic() {
        // set Mocks for measurementService, measurementArrayInDouble and hashMap
        MeasurementService measurementService = mock(MeasurementService.class);
        MeasurementRepoSubject measurementRepoSubject = mock(MeasurementRepoSubject.class);
        ArrayList<Double> measurementArrayInDouble = mock(ArrayList.class);
        Measurement measurement1 = mock(Measurement.class);
        Measurement measurement2 = mock(Measurement.class);
        Measurement measurement3 = mock(Measurement.class);
        Measurement measurement4 = mock(Measurement.class);
        Measurement measurement5 = mock(Measurement.class);
        HashMap<String, ArrayList<Measurement>> hashMap = new HashMap<>();
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

        when(measurementService.findErrorInFutureArray(anyInt(), any(), any(), anyDouble(), anyInt())).thenReturn(true);
        when(measurementService.findErrorInPastArray(anyInt(), any(), any(), anyDouble(), anyInt())).thenReturn(false);

        // call method to test
        boolean result = errorHandler.checkForErrorsPublic(index, measurementArrayInDouble, hashMap, type, tolerance);

        assertTrue(result);
    }




}
