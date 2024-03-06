package de.bht_berlin.paf2023.handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.measurements.AccelerationMeasurement;
import de.bht_berlin.paf2023.entity.measurements.SpeedMeasurement;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleModelRepo;
import de.bht_berlin.paf2023.service.MeasurementService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ThresholdErrorHandler implements MeasurementHandler {
    private MeasurementHandler nextHandler;

    private MeasurementRepoSubject measurementRepo;
    private VehicleModelRepo vehicleModelRepo;
    private MeasurementService measurementService;

    public ThresholdErrorHandler(MeasurementRepoSubject measurementRepo, MeasurementHandler nextHandler,
                                 MeasurementService measurementService, VehicleModelRepo vehicleModelRepo) {
        this.measurementRepo = measurementRepo;
        this.measurementService = measurementService;
        this.vehicleModelRepo = vehicleModelRepo;
        setNextHandler(nextHandler);
    }

    public void setNextHandler(MeasurementHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(Measurement measurement) {

    }

    @Override
    public void handle(Trip trip) {

    }

    @Override
    public void handle(HashMap<String, ArrayList<Measurement>> hashMap) {
        HashMap<String, ArrayList<Measurement>> processedHashMap = new HashMap<>();
        for (String type : hashMap.keySet()) {
            ArrayList<Measurement> values = hashMap.get(type);
            for (Measurement measurement : values) {
                if(measurement.getIsError()){
                    continue;
                }
                boolean isError = false;
                Float maxSpeed = measurement.getVehicle().getVehicleModel().getMaxSpeed();
                SpeedMeasurement tempSpeed = (SpeedMeasurement) measurement;
                Float currentSpeed = (float) tempSpeed.getSpeed();
                Float maxAcceleration = measurement.getVehicle().getVehicleModel().getMaxAcceleration();
                AccelerationMeasurement tempAcc = (AccelerationMeasurement) measurement;
                Float currentAcceleration = (float) tempAcc.getAcceleration();
                if (maxSpeed > currentSpeed || maxAcceleration > currentAcceleration) {
                    isError = true;
                }
                setErrorOnMeasurement(measurement, isError);
            }
            processedHashMap.put(type, values);
        }
        nextHandler.handle(processedHashMap);
    }
}
