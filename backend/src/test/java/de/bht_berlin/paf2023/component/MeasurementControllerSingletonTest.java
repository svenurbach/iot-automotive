package de.bht_berlin.paf2023.component;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.springframework.boot.origin.SystemEnvironmentOrigin;


@RunWith(MockitoJUnitRunner.class)
public class MeasurementControllerSingletonTest {

    @AfterEach
    void tearDown() {
    }

    @Mock
    private MeasurementRepoSubject repo;

    @Mock
    private VehicleRepo vehicleRepoMock;

    @Before
    public void setup() {
    }

    @Test
    public void testImportCSV() throws IOException {
        MeasurementRepoSubject repo = mock(MeasurementRepoSubject.class);


        List<List<String>> records =
                MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).readFile("../backend/imports/import-for-unittest" +
                        ".csv");
        List<HashMap> allReadOuts =
                MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).createHashMap(records);

        MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).createMeasurementEntities(allReadOuts);

        String[] desiredKeys = {"Latitude", "Acceleration", "Speed", "Steering"};
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

    @Test
    public void readFileFileNotFoundExceptionTest() {
        String file = "nonexistentfile.csv";
        assertThrows(FileNotFoundException.class, () -> {
            MeasurementControllerSingleton.getInstance(vehicleRepoMock, repo).readFile(file);
        });
    }
}