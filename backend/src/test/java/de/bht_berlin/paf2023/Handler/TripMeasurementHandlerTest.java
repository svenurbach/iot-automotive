package de.bht_berlin.paf2023.Handler;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.handler.MeasurementHandler;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the handle method of the TripHandler class.
 *
 * This test case verifies the functionality of the handle method in TripHandler,
 * assuming that TripHandler implements or extends the Handler interface or class.
 *
 * The test prepares a valid trip and simulates measurements for this trip.
 * The behavior of the measurementRepo is mocked using mock objects,
 * where the getAllMeasurementsFromTrip method is called to return the prepared measurements.
 *
 * Then, the handle method of the TripHandler is called with the prepared trip.
 * After calling the method, it verifies whether the measurements were grouped according to their type
 * and whether the grouped measurements were passed to the next handler as expected.
 */
class TripMeasurementHandlerTest {

    private TripMeasurementHandler tripHandler;
    private MeasurementRepoSubject measurementRepo;
    private MeasurementHandler nextHandler;

    @BeforeEach
    void setUp() {
        measurementRepo = mock(MeasurementRepoSubject.class);
        nextHandler = mock(MeasurementHandler.class);
        tripHandler = new TripMeasurementHandler(measurementRepo, nextHandler);
    }


    @Test
    void handleValidTrip() {
        long tripId = 1L;
        Trip trip = new Trip();
        trip.setId(tripId);
        // Mocking measurements for the trip
        List<Measurement> measurementsFromTrip = new ArrayList<>();
        Measurement measurement1 = new Measurement();
        measurement1.setMeasurementType("SpeedMeasurement");
        Measurement measurement2 = new Measurement();
        measurement2.setMeasurementType("AccelerationMeasurement");
        measurementsFromTrip.add(measurement1);
        measurementsFromTrip.add(measurement2);
        // Mocking behavior of the measurementRepo
        when(measurementRepo.getAllMeasurementsFromTrip(tripId)).thenReturn(measurementsFromTrip);
        tripHandler.handle(trip);
        // Verify that measurements are grouped by their type and passed to the next handler
        HashMap<String, ArrayList<Measurement>> expectedMap = new HashMap<>();
        expectedMap.put("SpeedMeasurement", new ArrayList<>(List.of(measurement1)));
        expectedMap.put("AccelerationMeasurement", new ArrayList<>(List.of(measurement2)));
        verify(nextHandler).handle(expectedMap);
    }
}


