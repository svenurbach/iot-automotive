package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.AccelerationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.AxisMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SteeringWheelMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.service.MeasurementService;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * Handler class responsible for handling threshold errors in measurements.
 */
public class ThresholdErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;
    private MeasurementRepoSubject measurementRepo;
    private VehicleModelRepo vehicleModelRepo;
    private MeasurementService measurementService;

    /**
     * Constructs a ThresholdErrorHandler with specified repos, services, and next handler.
     *
     * @param measurementRepo    MeasurementRepoSubject to use for retrieving measurements.
     * @param nextHandler        Next handler in the chain to pass the processed measurements to.
     * @param measurementService MeasurementService to use for processing measurements.
     * @param vehicleModelRepo   VehicleModelRepo to use for retrieving vehicle models.
     */
    public ThresholdErrorHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler,
                                 MeasurementService measurementService, VehicleModelRepo vehicleModelRepo) {
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
        this.vehicleModelRepo = vehicleModelRepo;
        setNextHandler(nextHandler);
    }

    /**
     * Sets the next handler in the chain.
     *
     * @param nextHandler The next handler to be set.
     */
    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Measurement measurement) {
        // Not implemented
    }

    @Override
    public void handle(Trip trip) {
        // Not implemented
    }

    /**
     * Handles a HashMap of measurements by processing them to identify threshold errors,
     * and passes the processed measurements to the next handler.
     *
     * @param hashMap HashMap contains measurements grouped by their type.
     *                Key: Type of measurements, Value: ArrayList of measurements of that type.
     */
    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        // Process the measurements and create a new HashMap with processed measurements
        HashMap<String, ArrayList<Measurement>> processedHashMap = hashMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> processMeasurements(entry.getValue()),
                        (oldValue, newValue) -> oldValue,
                        HashMap::new));
        // Passing the processed measurements to the next handler
        nextHandler.handle(processedHashMap);
    }

    /**
     * Processes the list of measurements by applying a processing function to each measurement.
     *
     * @param measurements ArrayList containing Measurement objects to be processed
     * @return ArrayList containing the processed Measurement objects
     */
    private ArrayList<Measurement> processMeasurements(ArrayList<Measurement> measurements) {
        // Using stream to map each Measurement to its processed version
        return measurements.stream()
                .map(this::processMeasurement)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // method for testing
    public ArrayList<Measurement> processMeasurementsPublic(ArrayList<Measurement> measurements){
        return processMeasurements(measurements);
    }

    /**
     * Processes individual measurement by applying specific rules based on its type.
     * If measurement indicates an error, it checks against predefined thresholds
     * for speed or acceleration and updates the error status accordingly.
     *
     * @param measurement Measurement object to be processed
     * @return Processed Measurement object
     */
    private Measurement processMeasurement(Measurement measurement) {
        if (measurement.getIsError() != null && !measurement.getIsError()) {
        } else {
            boolean isError = false;
            switch (measurement.getMeasurementType()) {
                case "SpeedMeasurement":
                    // Retrieve maximum speed allowed for the vehicle
                    Float maxSpeed = measurement.getVehicle().getVehicleModel().getMaxSpeed();
                    // Cast the measurement to SpeedMeasurement type
                    SpeedMeasurement speedMeasurement = (SpeedMeasurement) measurement;
                    // Retrieve current speed measurement
                    Float currentSpeed = (float) speedMeasurement.getSpeed();
                    // Checking if current speed exceeds the maximum allowed speed
                    if (currentSpeed >= maxSpeed || currentSpeed <= 0) {
                        isError = true;
                    }
                    break;
                case "AccelerationMeasurement":
                    Float maxAcceleration = measurement.getVehicle().getVehicleModel().getMaxAcceleration();
                    Float minAcceleration = measurement.getVehicle().getVehicleModel().getMinAcceleration();
                    AccelerationMeasurement accelerationMeasurement = (AccelerationMeasurement) measurement;
                    Float currentAcceleration = (float) accelerationMeasurement.getAcceleration();
                    if (currentAcceleration >= maxAcceleration || currentAcceleration <= minAcceleration) {
                        isError = true;
                    }
                    break;
                case "AxisMeasurement":
                    Float maxAxis = measurement.getVehicle().getVehicleModel().getMaxAxis();
                    Float minAxis = measurement.getVehicle().getVehicleModel().getMinAxis();
                    AxisMeasurement axisMeasurement = (AxisMeasurement) measurement;
                    Float currentAxis = axisMeasurement.getAxisAngle();
                    if (currentAxis >= maxAxis || currentAxis <= minAxis) {
                        isError = true;
                    }
                    break;
                case "SteeringWheelMeasurement":
                    Float maxSteeringWheel = measurement.getVehicle().getVehicleModel().getMaxSteeringWheel();
                    Float minSteeringWheel = measurement.getVehicle().getVehicleModel().getMinSteeringWheel();
                    SteeringWheelMeasurement steeringWheelMeasurement = (SteeringWheelMeasurement) measurement;
                    Float currentSteeringWheel = steeringWheelMeasurement.getSteeringWheelAngle();
                    if (currentSteeringWheel >= maxSteeringWheel || currentSteeringWheel <= minSteeringWheel) {
                        isError = true;
                    }
                    break;
            }
            // Set error status on the measurement object
            this.setErrorOnMeasurement(measurementRepo, measurement, isError);
        }
        // Return the processed measurement object
        return measurement;
    }

    // method for testing
    public Measurement processMeasurementPublic(Measurement measurement){
        return processMeasurement(measurement);
    }

    // method for testing
    @Override
    public void setErrorOnMeasurement(MeasurementRepoSubject measurementRepo, Measurement measurement, boolean isError) {
        measurementRepo.setIsError(measurement, isError);
    }
}
