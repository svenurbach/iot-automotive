package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.entity.measurements.*;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class MeasurementControllerSingleton {

    private static MeasurementControllerSingleton instance;
    private final String DELIMITER = ";";
    private final VehicleRepo vehicleRepo;
    private final MeasurementRepoSubject measurementRepo;

    @Autowired
    private MeasurementControllerSingleton(VehicleRepo vehicleRepo, MeasurementRepo measurementRepo) {
        this.vehicleRepo = vehicleRepo;
        this.measurementRepo = measurementRepo;
        System.out.println("Instance build");
    }

    public static MeasurementControllerSingleton getInstance(VehicleRepo vehicleRepo, MeasurementRepo measurementRepo) {
        if (instance == null) {
            instance = new MeasurementControllerSingleton(vehicleRepo, measurementRepo);
        }
        return instance;
    }

    public List readFile(String file) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<HashMap> createHashMap(List<List<String>> readOuts) {
//        System.out.println("readOuts");
//        System.out.println(readOuts);

        List<String> keys = new ArrayList<>();
        List<HashMap> allReadOuts = new ArrayList<>();

        for (int i = 0; i < readOuts.get(0).size(); i++) {
            keys.add(readOuts.get(0).get(i));
        }


        System.out.println("allReadOuts");

        for (int i = 1; i < readOuts.size(); i++) {
            HashMap<String, String> valuesMeasured = new HashMap<>();
            for (int j = 0; j < keys.size(); j++) {
                valuesMeasured.put(keys.get(j), readOuts.get(i).get(j));
            }
            allReadOuts.add(valuesMeasured);
//            System.out.println(allReadOuts);
        }
        return allReadOuts;
    }

    private static Date parseDateFromString(String dateString) {
        Date date = null;
        String pattern = "yyyy-MM-dd HH:mm:ss"; // Use the pattern that matches your date string format

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void testClass() {

    }

    public void createMeasurementEntities(List<HashMap> readOuts) {

        for (int i = 0; i < readOuts.size(); i++) {

            Date timestamp = parseDateFromString(readOuts.get(i).get("Timestamp").toString());

            Long vehicleid = Long.valueOf((readOuts.get(i).get("Vehicle").toString()));
//            System.out.println(readOuts.get(i));
            Vehicle existingVehicle = this.vehicleRepo.getById(vehicleid);

            if (readOuts.get(i).get("Accelaration") != null) {
                this.measurementRepo.save(new AccelerationMeasurement(timestamp,
                        Integer.parseInt(readOuts.get(i).get("Accelaration").toString()), existingVehicle));
            }
            if (readOuts.get(i).get("Axis_angle") != null) {
                this.measurementRepo.save(new AxisMeasurement(timestamp, Float.parseFloat(readOuts.get(i).get(
                        "Axis_angle").toString()), existingVehicle));
            }
            if (readOuts.get(i).get("Speed") != null) {
                this.measurementRepo.save(new SpeedMeasurement(timestamp, Integer.parseInt(readOuts.get(i).get("Speed").toString()),
                        existingVehicle));
            }

            if (readOuts.get(i).get("Fuel level") != null) {
                this.measurementRepo.save(new FuelMeasurement(timestamp, Integer.parseInt(readOuts.get(i).get("Fuel " +
                        "level").toString()),
                        existingVehicle));
            }

            if (readOuts.get(i).get("Steering Angle") != null) {
                this.measurementRepo.save(new SteeringWheelMeasurement(timestamp, Float.parseFloat(readOuts.get(i).get(
                        "Steering Angle").toString()),
                        existingVehicle));
            }

            if (readOuts.get(i).get("Latitude") != null) {
                List<Float> location = new ArrayList<>();
                location.add(Float.parseFloat(readOuts.get(i).get("Latitude").toString()));
                location.add(Float.parseFloat(readOuts.get(i).get("Longitude").toString()));
                this.measurementRepo.save(new LocationMeasurement(timestamp, location, existingVehicle));
            }

//            tire pressures
//            pedal measurements

        }
    }
}