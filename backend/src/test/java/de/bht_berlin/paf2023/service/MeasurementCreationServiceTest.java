package de.bht_berlin.paf2023.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.bht_berlin.paf2023.component.HandleSingleTripStrategy;
import de.bht_berlin.paf2023.observer.MeasurementObserver;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.strategy.TripHandlerStrategy;

@RunWith(MockitoJUnitRunner.class)

public class MeasurementCreationServiceTest {

    @AfterEach
    void tearDown() {
    }

    @Mock
    private TripRepo tripRepoMock;

    @Mock
    private MeasurementRepo measurementRepoOriginalMock;

    @Mock
    private MeasurementRepoSubject measurementRepoMock;

    @Captor
    private ArgumentCaptor<Measurement> measurementCaptor;

    @InjectMocks
    private TripService tripService;

    @InjectMocks
    MeasurementCreationService measurementCreationService;

    @Before
    public void setup() throws ParseException {

    }

    @Test
    public void runLineByLineImportTest() {
        String file = "../import-for-unittest.csv";

//        MeasurementCreationService measurementCreationService = mock(MeasurementCreationService.class);

        measurementCreationService.importFile(file);
        verify(measurementCreationService, timeout(10000)).runLineByLineImport();

    }
}