package de.bht_berlin.paf2023.component;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.*;

import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;


@RunWith(MockitoJUnitRunner.class)
public class MeasurementControllerSingletonTest {

    @AfterEach
    void tearDown() {
    }

    @Mock
    private VehicleRepo vehicleRepoMock;

    @Before
    public void setup() {
    }

    @Test
    public void testImportCSV() {

        MeasurementRepoSubject repo = mock(MeasurementRepoSubject.class);

        List<List<String>> records =
                MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).readFile("../import-for-unittest" +
                        ".csv");
        List<HashMap> allReadOuts = MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).createHashMap(records);

        MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).createMeasurementEntities(allReadOuts);

        String[] desiredKeys = {"Latitude", "Acceleration", "Speed", "Fuel level", "Axis_angle", "Steering"};
        int totalCount = 0;

        for (HashMap<String, Object[]> map : allReadOuts) {
            for (Map.Entry<String, Object[]> entry : map.entrySet()) {
                for (String desiredKey : desiredKeys) {
                    if (entry.getKey().equals(desiredKey)) {
                        totalCount++;
                        break;
                    }
                }
            }
        }
        verify(repo, times(totalCount)).addMeasurement(any(Measurement.class));
    }
}