package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.component.HandleSingleTripStrategy;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.LocationMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.TripRepo;
import de.bht_berlin.paf2023.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HandleSingleTripStrategyTest {


    @AfterEach
    void tearDown() {
    }

    @Mock
    private TripRepo tripRepoMock;

    @Mock
    private MeasurementRepo measurementRepoMock;

    @InjectMocks
    private MeasurementRepoSubject measurementRepoSubject;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void updateMeasurementTest() throws ParseException {

        TripService service = new TripService(tripRepoMock, measurementRepoSubject);

        HandleSingleTripStrategy strategy = new HandleSingleTripStrategy(tripRepoMock, measurementRepoSubject);
        service.changeTripHandlerStrategy(strategy);

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

        measurementRepoSubject.addMeasurement(locationMeasurement);

        verify(tripRepoMock, atLeast(1)).save(any(Trip.class));
    }
}