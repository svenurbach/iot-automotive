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
        MeasurementTimeSortHandler measurementTimeSortHandler = new MeasurementTimeSortHandler(measurementRepoSubject, nextHandler);
        HashMap<String, ArrayList<Measurement>> hashMap = new HashMap<>();
        ArrayList<Date> timestampArray = new ArrayList<>();
        // add timestamps to list
        Date timestamp1 = new Date();
        timestampArray.add(timestamp1);
        Date timestamp2 = new Date(System.currentTimeMillis() - 1000);
        timestampArray.add(timestamp2);
        Date timestamp3 = new Date(System.currentTimeMillis() - 3000);
        timestampArray.add(timestamp3);

        ArrayList<Measurement> speedMeasurements = new ArrayList<>();
        int timestampIndex = 0;
        for (int i = 0; i < 3; i++) {
            Measurement measurement = new Measurement();
            measurement.setMeasurementType("SpeedMeasurement");
            measurement.setTimestamp(timestampArray.get(timestampIndex));
            speedMeasurements.add(measurement);
            timestampIndex++;
        }

        hashMap.put("SpeedMeasurement", speedMeasurements);

        HashMap<String, ArrayList<Measurement>> sortedHashMap = measurementTimeSortHandler.processHashMapPublic(hashMap);

        ArrayList<Date> timestamps = new ArrayList<>();
        for (ArrayList<Measurement> measurementList : sortedHashMap.values()) {
            for (Measurement measurement : measurementList) {
                timestamps.add(measurement.getTimestamp());
            }
        }
        System.out.println("timestamps: " + timestamps);
        boolean isOrdered = Ordering.natural().isOrdered(timestamps);
        assertTrue(isOrdered);
    }
}

