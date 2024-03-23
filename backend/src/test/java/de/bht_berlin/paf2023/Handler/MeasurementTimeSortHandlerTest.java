package de.bht_berlin.paf2023.Handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.handler.MeasurementHandler;
import de.bht_berlin.paf2023.handler.MeasurementTimeSortHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.Ordering;

/**
 * Tests the handle method of MeasurementTimeSortHandler class when given a hashmap of measurements. Sets up necessary
 * mocks and objects, creates a hashmap of measurements with timestamps, invokes the method to handle the measurements,
 * and asserts that the resulting hashmap has measurements sorted by their timestamps.
 */
class MeasurementTimeSortHandlerTest {

    private MeasurementHandler nextHandler;
    private MeasurementRepoSubject measurementRepoSubject;

    @BeforeEach
    void setUp() {
        nextHandler = mock(MeasurementHandler.class);
        measurementRepoSubject = mock(MeasurementRepoSubject.class);
    }

    @Test
    void handle_SortedMeasurements() {
        // Create an instance of MeasurementTimeSortHandler with mock dependencies
        MeasurementTimeSortHandler measurementTimeSortHandler = new MeasurementTimeSortHandler(measurementRepoSubject, nextHandler);
        // Create a HashMap to store measurements grouped by type
        HashMap<String, ArrayList<Measurement>> hashMap = new HashMap<>();
        // Create an ArrayList to store timestamps for test measurements
        ArrayList<Date> timestampArray = new ArrayList<>();
        // Add timestamps to the timestampArray
        Date timestamp1 = new Date();
        timestampArray.add(timestamp1);
        Date timestamp2 = new Date(System.currentTimeMillis() - 1000);
        timestampArray.add(timestamp2);
        Date timestamp3 = new Date(System.currentTimeMillis() - 3000);
        timestampArray.add(timestamp3);

        // Create an ArrayList to store SpeedMeasurement objects
        ArrayList<Measurement> speedMeasurements = new ArrayList<>();
        int timestampIndex = 0;
        // Create three SpeedMeasurement objects and assign timestamps from timestampArray
        for (int i = 0; i < 3; i++) {
            Measurement measurement = new Measurement();
            measurement.setMeasurementType("SpeedMeasurement");
            measurement.setTimestamp(timestampArray.get(timestampIndex));
            speedMeasurements.add(measurement);
            timestampIndex++;
        }

        // Add speedMeasurements to the hashMap under the key "SpeedMeasurement"
        hashMap.put("SpeedMeasurement", speedMeasurements);

        // Call method to be tested
        HashMap<String, ArrayList<Measurement>> sortedHashMap = measurementTimeSortHandler.processHashMapPublic(hashMap);

        // Create an ArrayList to store timestamps from sortedHashMap
        ArrayList<Date> timestamps = new ArrayList<>();
        // Iterate through sortedHashMap and extract timestamps
        for (ArrayList<Measurement> measurementList : sortedHashMap.values()) {
            for (Measurement measurement : measurementList) {
                timestamps.add(measurement.getTimestamp());
            }
        }
        // Check if the timestamps are in ascending order
        boolean isOrdered = Ordering.natural().isOrdered(timestamps);
        // Assert that the timestamps are correctly sorted
        assertTrue(isOrdered);
    }
}

