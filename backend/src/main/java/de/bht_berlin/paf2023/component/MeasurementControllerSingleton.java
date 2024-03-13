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
    private MeasurementControllerSingleton(VehicleRepo vehicleRepo, MeasurementRepoSubject measurementRepo) {
        this.vehicleRepo = vehicleRepo;
        this.measurementRepo = measurementRepo;
    }

    public static MeasurementControllerSingleton getInstance(VehicleRepo vehicleRepo, MeasurementRepoSubject measurementRepo) {
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

        List<String> keys = new ArrayList<>();
        List<HashMap> allReadOuts = new ArrayList<>();

        for (int i = 0; i < readOuts.get(0).size(); i++) {
            keys.add(readOuts.get(0).get(i));
        }

        for (int i = 1; i < readOuts.size(); i++) {
            HashMap<String, String> valuesMeasured = new HashMap<>();
            for (int j = 0; j < keys.size(); j++) {
                valuesMeasured.put(keys.get(j), readOuts.get(i).get(j));
            }
            allReadOuts.add(valuesMeasured);
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

    public List<HashMap> readFileLineByLine(String file, List<String> columnHeaders, int currentLineIndex) {
        List<HashMap> readout = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<List<String>> valuesInList = new ArrayList<>();
            valuesInList.add(columnHeaders);
            for (int i = 0; i < currentLineIndex; i++) {
                br.readLine();
            }

            if ((line = br.readLine()) != null) {
                List<String> values = Arrays.asList(line.split(DELIMITER));
                valuesInList.add(values);
                readout = createHashMap(valuesInList);
//                createMeasurementEntities(readout);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readout;
    }

    public List<String> getCSVColumnHeaders(String file) {
        List<String> columnHeaders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            if ((line = br.readLine()) != null) {
                columnHeaders = Arrays.asList(line.split(";")); // Assuming ';' is your delimiter
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return columnHeaders;
    }

    public void createMeasurementEntities(List<HashMap> readOuts) {


        for (int i = 0; i < readOuts.size(); i++) {

            Date timestamp = parseDateFromString(readOuts.get(i).get("Timestamp").toString());

            Long vehicleid = Long.valueOf((readOuts.get(i).get("Vehicle").toString()));
            Vehicle existingVehicle = this.vehicleRepo.getById(vehicleid);

            if (readOuts.get(i).get("Latitude") != null) {
                List<Float> location = new ArrayList<>();
                location.add(Float.parseFloat(readOuts.get(i).get("Latitude").toString()));
                location.add(Float.parseFloat(readOuts.get(i).get("Longitude").toString()));
                this.measurementRepo.addMeasurement(new LocationMeasurement(timestamp, location, existingVehicle));
            }

            if (readOuts.get(i).get("Acceleration") != null) {
                this.measurementRepo.addMeasurement(new AccelerationMeasurement(timestamp,
                        Integer.parseInt(readOuts.get(i).get("Acceleration").toString()), existingVehicle));
            }
            if (readOuts.get(i).get("Axis_angle") != null) {
                this.measurementRepo.addMeasurement(new AxisMeasurement(timestamp, Float.parseFloat(readOuts.get(i).get(
                        "Axis_angle").toString()), existingVehicle));
            }
            if (readOuts.get(i).get("Speed") != null) {
                this.measurementRepo.addMeasurement(new SpeedMeasurement(timestamp, Integer.parseInt(readOuts.get(i).get("Speed").toString()),
                        existingVehicle));
            }
            if (readOuts.get(i).get("Steering") != null) {
                this.measurementRepo.addMeasurement(new SteeringWheelMeasurement(timestamp, Float.parseFloat(readOuts.get(i).get(
                        "Steering").toString()),
                        existingVehicle));
            }

//            tire pressures
//            pedal measurements

        }
    }
}