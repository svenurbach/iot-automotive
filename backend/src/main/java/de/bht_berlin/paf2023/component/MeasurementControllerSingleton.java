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

    /**
     * create singleton instance
     *
     * @param vehicleRepo     vehicle repository
     * @param measurementRepo measurement repositry
     * @return return singleton instance
     */
    public static MeasurementControllerSingleton getInstance(VehicleRepo vehicleRepo, MeasurementRepoSubject measurementRepo) {
        if (instance == null) {
            instance = new MeasurementControllerSingleton(vehicleRepo, measurementRepo);
        }
        return instance;
    }

    /**
     * read out csv file and create a nested list of strings for each line of file
     *
     * @param file string path of csv file
     * @return nested list of strings
     */
    public List readFile(String file) {
        List<List<String>> records = new ArrayList<>();

        /**
         * read file line by line and add them as string array to list
         */
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

    /**
     * create hashmap from readout
     *
     * @param readOuts nested list of strings
     * @return hashmap containing values and keys from csv table header
     */
    public List<HashMap> createHashMap(List<List<String>> readOuts) {

        List<String> keys = new ArrayList<>();
        List<HashMap> allReadOuts = new ArrayList<>();

        // get keys of csv table header
        for (int i = 0; i < readOuts.get(0).size(); i++) {
            keys.add(readOuts.get(0).get(i));
        }

        // get values from each line
        for (int i = 1; i < readOuts.size(); i++) {
            HashMap<String, String> valuesMeasured = new HashMap<>();
            for (int j = 0; j < keys.size(); j++) {
                valuesMeasured.put(keys.get(j), readOuts.get(i).get(j));
            }
            allReadOuts.add(valuesMeasured);
        }
        return allReadOuts;
    }

    /**
     * parse date strings to date objects
     *
     * @param dateString string of date
     * @return date object
     */
    private static Date parseDateFromString(String dateString) {
        Date date = null;

        // define pattern for parsing
        String pattern = "yyyy-MM-dd HH:mm:ss";

        // create new date object
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * read out a csv file line by line and create measurement from each line
     *
     * @param file             string path to csv file
     * @param columnHeaders    list of strings containing column headers
     * @param currentLineIndex index of line to read from csv file
     * @return
     */
    public List<HashMap> readFileLineByLine(String file, List<String> columnHeaders, int currentLineIndex) {
        List<HashMap> readout = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // initialize new list
            List<List<String>> valuesInList = new ArrayList<>();
            // add column headers to list
            valuesInList.add(columnHeaders);

            // set cursor
            for (int i = 0; i < currentLineIndex; i++) {
                br.readLine();
            }

            // add values of line to new list
            if ((line = br.readLine()) != null) {
                List<String> values = Arrays.asList(line.split(DELIMITER));
                valuesInList.add(values);

                // initialize hashmap of list containing a line's values
                readout = createHashMap(valuesInList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readout;
    }

    /**
     * get column headers of a given csv file
     *
     * @param file string path to csv file
     * @return list of string containng column headers
     */
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

    /**
     * create measurement entites from a given list of hashmaps
     *
     * @param readOuts list of hashmap containing one measurement
     */
    public void createMeasurementEntities(List<HashMap> readOuts) {

        // iterate through list of hashmaps
        for (int i = 0; i < readOuts.size(); i++) {

            // create date object for timestamp
            Date timestamp = parseDateFromString(readOuts.get(i).get("Timestamp").toString());

            // parse vehicle id
            Long vehicleid = Long.valueOf((readOuts.get(i).get("Vehicle").toString()));
            // retrieve the vehicle from repo
            Vehicle existingVehicle = this.vehicleRepo.getById(vehicleid);

            // create measurement type according to key and set value along with vehicle and timestamp
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
        }
    }
}