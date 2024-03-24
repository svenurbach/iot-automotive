package de.bht_berlin.paf2023.Handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.VehicleModel;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.handler.MeasurementHandler;
import de.bht_berlin.paf2023.handler.ThresholdErrorHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests the functionality of ThresholdErrorHandler class. Sets up mocks for dependencies, tests processing of
 * measurements with and without errors, and verifies setting error state on measurements.
 */
@RunWith(MockitoJUnitRunner.class)
class ThresholdErrorHandlerTest {

    @Mock
    private MeasurementHandler nextHandler;
    private VehicleModelRepo vehicleModelRepo;
    private MeasurementService measurementService;


    @BeforeEach
    void setUp() {
        nextHandler = mock(MeasurementHandler.class);
    }


    /**
     * Tests processing of a measurement with no error. Sets up necessary mocks and objects, invokes the method to
     * process the measurement, and asserts that the measurement has no error after processing.
     */
    @Test
    void processMeasurementTest_WithNoError() {
        // Arrange
        MeasurementRepoSubject measurementRepoSubject = new MeasurementRepoSubject();
        MeasurementRepo measurementRepo = mock(MeasurementRepo.class);
        measurementRepoSubject.setMeasurementRepo(measurementRepo);
        ThresholdErrorHandler handler = new ThresholdErrorHandler(measurementRepoSubject, nextHandler, measurementService, vehicleModelRepo);
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setMaxSpeed(100.0f);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel(vehicleModel);
        SpeedMeasurement measurement = new SpeedMeasurement();
        measurement.setMeasurementType("SpeedMeasurement");
        measurement.setVehicle(vehicle);
        measurement.setSpeed(50);

        // Call method to test
       Measurement afterProcess = handler.processMeasurementPublic(measurement);

        // Call the method to be tested
        assertFalse(afterProcess.getIsError());
    }

    /**
     * Tests processing of a measurement with an error. Sets up necessary mocks and objects, invokes the method to
     * process the measurement, and asserts that the measurement has an error after processing.
     */
    @Test
    void processMeasurementTest_WithError() {
        // Arrange
        MeasurementRepoSubject measurementRepoSubject = new MeasurementRepoSubject();
        MeasurementRepo measurementRepo = mock(MeasurementRepo.class);
        measurementRepoSubject.setMeasurementRepo(measurementRepo);
        ThresholdErrorHandler handler = new ThresholdErrorHandler(measurementRepoSubject, nextHandler, measurementService, vehicleModelRepo);
        VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setMaxSpeed(100.0f);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel(vehicleModel);
        SpeedMeasurement measurement = new SpeedMeasurement();
        measurement.setMeasurementType("SpeedMeasurement");
        measurement.setVehicle(vehicle);
        measurement.setSpeed(500);

        // Call the method to be tested
        Measurement afterProcess = handler.processMeasurementPublic(measurement);

        // What I expected
        assertTrue(afterProcess.getIsError());
    }

    /**
     * Tests setting error state on a measurement. Sets up necessary mocks and objects, calls the method to set error
     * on a measurement, and verifies that the error state was correctly set on the measurement.
     */
    @Test
    void setErrorOnMeasurementTest() {
        // Create mocks for MeasurementRepoSubject, Measurement and MeasurementHandler (Interface)
        MeasurementRepoSubject measurementRepoSubject = mock(MeasurementRepoSubject.class);
        MeasurementRepo measurementRepo = mock(MeasurementRepo.class);
        measurementRepoSubject.setMeasurementRepo(measurementRepo);
        ThresholdErrorHandler handler = new ThresholdErrorHandler(measurementRepoSubject, nextHandler, measurementService, vehicleModelRepo);
        Measurement measurement = new Measurement();

        // Set up test data
        boolean isError = true;

        // Call the method to be tested
        handler.setErrorOnMeasurement(measurementRepoSubject, measurement, isError);

        // Verify that setIsError method was called on measurementRepo with correct parameters
        verify(measurementRepoSubject, times(1)).setIsError(measurement, isError);
    }
}

