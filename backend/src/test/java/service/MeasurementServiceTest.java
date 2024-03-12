package service;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import de.bht_berlin.paf2023.service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MeasurementServiceTest {

    @Mock

    private MeasurementRepo measurementRepo;

    private MeasurementRepoSubject measurementRepoSubject;


    @InjectMocks
    private MeasurementService measurementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        measurementRepoSubject = new MeasurementRepoSubject();
//        measurementService = new MeasurementService(measurementRepoSubject);
    }

//    @Test
//    public void testRoundToTwoDecimalPlaces_WithIntegerInput() {
//        double value = 15.0;
//        double result = measurementService.roundToTwoDecimalPlaces(value);
//        assertEquals(15.0, result);
//    }
//
//    @Test
//    public void testRoundToTwoDecimalPlaces_WithFractionalInput() {
//        double value = 15.555;
//        double result = measurementService.roundToTwoDecimalPlaces(value);
//        assertEquals(15.56, result);
//    }
//
//    @Test
//    public void testRoundToTwoDecimalPlaces_WithNegativeInput() {
//        double value = -15.555;
//        double result = measurementService.roundToTwoDecimalPlaces(value);
//        assertEquals(-15.56, result);
//    }
//
//    @Test
//    public void testRoundToTwoDecimalPlaces_WithZeroInput() {
//        double value = 0.0;
//        double result = measurementService.roundToTwoDecimalPlaces(value);
//        assertEquals(0.0, result);
//    }
//
//    @Test
//    public void testCalculateAverageMeasurements_WithNonEmptyList() {
//        ArrayList<Double> measurementArrayList = new ArrayList<>();
//        measurementArrayList.add(10.0);
//        measurementArrayList.add(20.0);
//        measurementArrayList.add(30.0);
//        double result = measurementService.calculateAverageMeasurements(measurementArrayList);
//        assertEquals(20.0, result);
//    }
//
//    @Test
//    public void testCalculateAverageMeasurements_WithEmptyList() {
//        ArrayList<Double> measurementArrayList = new ArrayList<>();
//        double result = measurementService.calculateAverageMeasurements(measurementArrayList);
//        assertEquals(0.0, result);
//    }
//
//    @Test
//    public void testCalculateAverageMeasurements_WithSingleValue() {
//        ArrayList<Double> measurementArrayList = new ArrayList<>();
//        measurementArrayList.add(15.0);
//        double result = measurementService.calculateAverageMeasurements(measurementArrayList);
//        assertEquals(15.0, result);
//    }
//
//
//    @Test
//    public void testFindMeasurementError_WithNotEnoughValues() {
//        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 20.0));
//        int comparativeValuesArraySize = 5;
//        Double tolerance = 0.1;
//        MeasurementService measurementService = new MeasurementService(measurementRepoSubject);
//        boolean result = measurementService.findMeasurementError(measurementArrayInDouble, comparativeValuesArraySize, tolerance);
//        assertTrue(result);
//    }

//    @Test
//    public void testFindMeasurementError_WithNoError() {
//        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0, 25.0, 35.0));
////        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 10.0, 10.0, 10.0, 10.0, 10.0));
//        int comparativeValuesArraySize = 3;
//        Double tolerance = 0.9;
//        MeasurementService measurementService = new MeasurementService(measurementRepoSubject);
//        boolean result = measurementService.findMeasurementError(measurementArrayInDouble, comparativeValuesArraySize, tolerance);
//        assertFalse(result);
//    }

//    @Test
//    public void testFindMeasurementError_WithError() {
//        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 20000.0, 15.0, 25.00, 27.00, 30.0, 15.0, 5.0));
////        ArrayList<Double> measurementArrayInDouble = new ArrayList<>(Arrays.asList(10.0, 10.0, 10.0, 10.0, 10.0, 10.0));
//        int comparativeValuesArraySize = 3;
//        Double tolerance = 0.1;
//        MeasurementService measurementService = new MeasurementService(measurementRepoSubject);
//        boolean result = measurementService.findMeasurementError(measurementArrayInDouble, comparativeValuesArraySize, tolerance);
//        assertTrue(result);
//    }


}
