package Handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.handler.ComparitiveListErrorHandler;
import de.bht_berlin.paf2023.handler.MeasurementHandler;
import de.bht_berlin.paf2023.handler.ThresholdErrorHandler;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ThresholdErrorHandlerTest {
    @Mock
    private MeasurementRepo measurementRepo;

    @Mock
    private MeasurementRepoSubject measurementRepoSubject;
    @InjectMocks
    private ThresholdErrorHandler thresholdErrorHandler;

    @Mock
    private MeasurementHandler nextHandler;
    private VehicleModelRepo vehicleModelRepo;
    private MeasurementService measurementService;


    @BeforeEach
    void setUp() {
//        measurementRepo = mock(MeasurementRepo.class);
        nextHandler = mock(MeasurementHandler.class);
        measurementRepoSubject = mock(MeasurementRepoSubject.class);
        thresholdErrorHandler = mock(ThresholdErrorHandler.class);
    }
//    @Test
//    void testProcessMeasurements() {
//        // Create a list of measurements to be processed
//        ArrayList<Measurement> measurements = new ArrayList<>();
//        // Populate the list with mock measurements
//         Measurement measurement1 = mock(Measurement.class);
//         Measurement measurement2 = mock(Measurement.class);
//         measurements.add(measurement1);
//         measurements.add(measurement2);
//
//        // Call the method to be tested
//        ArrayList<Measurement> processedMeasurements = thresholdErrorHandler.processMeasurementsPublic(measurements);
//
//        // Assert that the size of the processed list is the same as the original list
//        Assertions.assertEquals(measurements.size(), processedMeasurements.size());
//
//        // Verify that processMeasurement() method was called for each measurement in the list
//        for (Measurement measurement : measurements) {
//            verify(thresholdErrorHandler, times(1)).processMeasurementPublic(measurement);
//        }
//    }

//    @Test
//    void processMeasurement_SpeedMeasurementBelowThreshold_NoError() {
//        // Arrange
////        MeasurementRepoSubject measurementRepo = new MeasurementRepoSubject();
//        ThresholdErrorHandler thresholdErrorHandler = new ThresholdErrorHandler(measurementRepoSubject, nextHandler, measurementService, vehicleModelRepo);
//        VehicleModel vehicleModel = new VehicleModel();
//        vehicleModel.setMaxSpeed(100.0f);
//        Vehicle vehicle = new Vehicle();
//        vehicle.setVehicleModel(vehicleModel);
//        SpeedMeasurement measurement = new SpeedMeasurement();
//        measurement.setMeasurementType("SpeedMeasurement");
//        measurement.setVehicle(vehicle);
//        measurement.setSpeed(50);
//
//        // Act
//        thresholdErrorHandler.processMeasurementPublic(measurement);
//        System.out.println(measurement);
//
//        // Assert
//        assertFalse(measurement.getIsError());
//    }

    @Test
    void testSetErrorOnMeasurement() {
        // Create mocks for MeasurementRepoSubject, Measurement and MeasurementHandler (Interface)
        MeasurementRepoSubject measurementRepo = mock(MeasurementRepoSubject.class);
        MeasurementHandlerTest measurementHandler = mock(MeasurementHandlerTest.class);
        Measurement measurement = mock(Measurement.class);

        // Set up test data
        boolean isError = true;

        // Call the method to be tested
        thresholdErrorHandler.setErrorOnMeasurement(measurementRepoSubject, measurement, isError);

        // Verify that setIsError method was called on measurementRepo with correct parameters
        verify(measurementRepoSubject, times(1)).setIsError(measurement, isError);
    }
}

