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
import de.bht_berlin.paf2023.entity.Vehicle;
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
public class TripServiceTest {


    @AfterEach
    void tearDown() {
    }

    @Mock
    private TripRepo tripRepoMock;

    @Mock
    private MeasurementRepo measurementRepoOriginalMock;

    @Mock
    private MeasurementRepoSubject measurementRepoMock;

    @InjectMocks
    private MeasurementRepoSubject measurementRepoMock2;


    @Captor
    private ArgumentCaptor<Measurement> measurementCaptor;

    @InjectMocks
    private TripService tripService;

    @Before
    public void setup() throws ParseException {
//        MockitoAnnotations.openMocks(this);
//        tripService = new TripService(tripRepoMock, measurementRepoMock);
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a spy for TripService
        tripService = spy(new TripService(tripRepoMock, measurementRepoMock));
    }

    @Test
    public void testStartTrip() throws ParseException {

        LocationMeasurement locationMeasurement = mock(LocationMeasurement.class);
        Date date_correct = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-02-18 06:01:33");
        Date date_wrong = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-02-18 06:01:33");

        when(locationMeasurement.getTimestamp()).thenReturn(date_correct);

        when(locationMeasurement.getLatitude()).thenReturn(52.5237569f);
        when(locationMeasurement.getLongitude()).thenReturn(13.3344698f);

        Trip resultTrip = tripService.startTrip(locationMeasurement, tripRepoMock);
        resultTrip.setTrip_start(date_correct);

        assertEquals(resultTrip.getTrip_start(), date_correct);
        assertNotEquals(resultTrip.getTrip_start(), date_wrong);
        assertEquals(resultTrip.getStart_latitude(), 52.5237569f, 0.000001f);
        assertEquals(resultTrip.getStart_longitude(), 13.3344698f, 0.000001f);
        assertEquals(resultTrip.getState(), Trip.TripState.RUNNING);
    }


    @Test
    public void testGetAverageSpeed() throws ParseException {
        Trip trip = new Trip();
        trip.setId(1L);

        List<Measurement> measurements = new ArrayList<>();
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-02-18 06:01:33");
        int speed = 30;
        long dateIterator = 1;

        for (int i = 0; i < 5; i++) {
            Date newDate = new Date(date.getTime() + (dateIterator * 1000 * 60));
            SpeedMeasurement speedMeasurement = mock(SpeedMeasurement.class);
            when(speedMeasurement.getTimestamp()).thenReturn(newDate);
            when(speedMeasurement.getSpeed()).thenReturn(speed);
            measurements.add(speedMeasurement);
            dateIterator++;
        }

        when(measurementRepoMock.findMeasurementTypeInTrip("SpeedMeasurement", 1L)).thenReturn(measurements);

        Double averageSpeed = tripService.getAverageSpeedForTrip(trip);

        assertEquals(Double.valueOf(30.0), averageSpeed);
    }

    @Test
    public void testGetTotalDistance() throws ParseException {
        Trip trip = new Trip();
        trip.setId(1L);

        List<Measurement> measurements = new ArrayList<>();
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-02-18 06:01:33");
        int speed = 50;
        long dateIterator = 1;

        for (int i = 0; i < 5; i++) {
            Date newDate = new Date(date.getTime() + (dateIterator * 1000 * 60 * 60));
            SpeedMeasurement speedMeasurement = mock(SpeedMeasurement.class);
            when(speedMeasurement.getTimestamp()).thenReturn(newDate);
            when(speedMeasurement.getSpeed()).thenReturn(speed);
            measurements.add(speedMeasurement);
            dateIterator++;
        }

        when(measurementRepoMock.findMeasurementTypeInTrip("SpeedMeasurement", 1L)).thenReturn(measurements);

        Double totalDistance = tripService.getTotalDistanceForTrip(trip);

        assertEquals(Double.valueOf(200.0), totalDistance);
    }

    @Test
    public void updateMeasurementTest() throws ParseException {

        TripService service = new TripService(tripRepoMock, measurementRepoMock2);
        HandleSingleTripStrategy strategy = new HandleSingleTripStrategy(tripRepoMock, measurementRepoMock2);
        service.changeTripHandlerStrategy(strategy);
        HandleSingleTripStrategy strategySpy = spy(strategy);
        TripService serviceSpy = spy(service);

        Vehicle vehicleMock = mock(Vehicle.class);
        when(vehicleMock.getId()).thenReturn(1L);

        LocationMeasurement locationMeasurement = mock(LocationMeasurement.class);
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-02-18 06:01:33");
        when(locationMeasurement.getTimestamp()).thenReturn(date);

        when(locationMeasurement.getTimestamp()).thenReturn(date);

        when(locationMeasurement.getLatitude()).thenReturn(52.5237569f);
        when(locationMeasurement.getLongitude()).thenReturn(13.3344698f);
        when(locationMeasurement.getVehicle()).thenReturn(vehicleMock);
        when(locationMeasurement.getMeasurementType()).thenReturn("LocationMeasurement");

        measurementRepoMock2.addMeasurement(locationMeasurement);

        verify(strategySpy, atLeast(1)).addData(locationMeasurement);
//        verify(repo, times(totalCount)).addMeasurement(any(Measurement.class));

    }
}