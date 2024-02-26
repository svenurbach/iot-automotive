package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.TripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepo repository;

    // User Story: Messefehler/Inkonsistenzen erkennen
    // https://trello.com/c/1bafUTDM/37-user-story-messfehler-inkonsistenzen-erkennen

    @Autowired
    public MeasurementService(MeasurementRepo repository) {
        this.repository = repository;
    }

//writeMetric(metric, value, vehicle, trip, )
//    write single metric into db

//    readMeasurements(vehicle)
//    reads all existing metric for a vehicle

//    readAllMeasurementsTrip(vehicle, trip)
//   reads all existing measurements for a vehicle belonging to a trip

//    validateMeasurement(vehicle)
//        validates a metric for errors and returns boolean

//    showError(vehicle)
//        show error in measurement

    public String getMeasurements(long id){
         return repository.findById(id).get().getMeasuredValue().toString();
    }

    // returns Array with n past measurements
    public Measurement[] getAmountOfPastMeasurements(int n, String measurementType){
        List<Measurement> measurements = repository.findTopNByOrderByTimestampDesc(n, measurementType);
        Measurement[] measurementsArray = new Measurement[n];
        for (int i = 0; i < n; i++){
            measurementsArray[i] = measurements.get(i);
        }
        return measurementsArray;
    }

    public Long[] parseMeasurementArrayToLong(Measurement[] measurementsArray){
        Long[] measurementArrayInLong = new Long[0];
        int pos = 0;
        for (int i = 0; i < measurementsArray.length; i++){
            if(measurementsArray[i].getMeasurementType() == "AccelerationMeasurement"){
                AccelerationMeasurement accelerationMeasurement = (AccelerationMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(accelerationMeasurement.getAcceleration());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            if(measurementsArray[i].getMeasurementType() == "AxisMeasurement"){
                AxisMeasurement axisMeasurement = (AxisMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(axisMeasurement.getAxis());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            if(measurementsArray[i].getMeasurementType() == "FuelMeasurement"){
                FuelMeasurement fuelMeasurement = (FuelMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(fuelMeasurement.getFuelLevel());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            if(measurementsArray[i].getMeasurementType() == "SpeedMeasurement"){
                SpeedMeasurement speedMeasurement = (SpeedMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(speedMeasurement.getSpeed());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            if(measurementsArray[i].getMeasurementType() == "SteeringWheelMeasurement"){
                SteeringWheelMeasurement steeringWheelMeasurement = (SteeringWheelMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(steeringWheelMeasurement.getSteeringWheel());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            if(measurementsArray[i].getMeasurementType() == "TirePressureMeasurement"){
                TirePressureMeasurement tirePressureMeasurement = (TirePressureMeasurement) measurementsArray[i];
                Long measurementInLong = Long.valueOf(tirePressureMeasurement.getTirePressure());
                measurementArrayInLong[i+pos] = measurementInLong;
            }
            pos += 1;
        }
    }

    // calculate average
    public Long calculateAverageMeasurements(Measurement[] measurementsArray){
        String measurementType = "";
        long sum = 0;
        for (Measurement measurement : measurementsArray) {
            if (measurementType == ""){
                measurementType = measurement.getMeasurementType();
            }
            long val = 0;
            if (measurement.getMeasurementType() == "SpeedMeasurement" && measurement.getMeasurementType() == measurementType){
               SpeedMeasurement speedMeasurement = (SpeedMeasurement) measurement;
                val = speedMeasurement.getSpeed();
            }
            if (measurement.getMeasurementType() == "FuelMeasurement"){
                FuelMeasurement fuelMeasurement = (FuelMeasurement) measurement;
                val = fuelMeasurement.getFuelLevel();
            }
            sum += val;
        }
        if (measurementsArray.length != 0) {
            long average = sum / measurementsArray.length;
            return average;
        } else {
            return 0L;
        }
    }


}
