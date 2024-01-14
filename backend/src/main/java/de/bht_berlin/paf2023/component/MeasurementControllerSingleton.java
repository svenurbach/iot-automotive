package de.bht_berlin.paf2023.component;

import de.bht_berlin.paf2023.entity.measurements.*;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class MeasurementControllerSingleton {

    private static MeasurementControllerSingleton instance;
    private final String DELIMITER = ";";

    private MeasurementControllerSingleton() {
        System.out.println("Instance build");
    }

    public static MeasurementControllerSingleton getInstance() {
        if (instance == null) {
            instance = new MeasurementControllerSingleton();
        }
        return instance;
    }

    public List readFile() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
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

        HashMap<String, String> valuesMeasured = new HashMap<>();

        for (int i = 1; i < readOuts.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                valuesMeasured.put(keys.get(j), readOuts.get(i).get(j));
            }
            allReadOuts.add(valuesMeasured);
        }
        return allReadOuts;
    }

    public void createMeasurementEntities(List<HashMap> readOuts) {

        for (int i = 0; i < readOuts.size(); i++) {


            if (readOuts.get(i).get("Accelaration") != null) {
                new AccelerationMeasurement((Date) readOuts.get(i).get("Timestamp"), (Integer) readOuts.get(i).get("Accelaration"));
            }
            if (readOuts.get(i).get("Axis_angle") != null) {
                new AxisMeasurement((Date) readOuts.get(i).get("Timestamp"), (Float) readOuts.get(i).get("Axis_angle"));
            }
            if (readOuts.get(i).get("Speed") != null) {
                new SpeedMeasurement((Date) readOuts.get(i).get("Timestamp"), (Integer) readOuts.get(i).get("Speed"));
            }

            if (readOuts.get(i).get("Fuel level") != null) {
                new FuelMeasurement((Date) readOuts.get(i).get("Timestamp"), (Integer) readOuts.get(i).get("Fuel level"));
            }

        }
    }
}