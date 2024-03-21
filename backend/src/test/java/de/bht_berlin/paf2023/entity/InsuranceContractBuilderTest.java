package de.bht_berlin.paf2023.entity;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class InsuranceContractBuilderTest {

    @Test
    public void InsuranceContractBuilder() {
        String policyNumber = "123-456-789";
        Long deductible = 150L;
        Long contractDistance = 10000L;
        Float contractPrice = 550.50f;
        Date begin = new Date();
        Vehicle vehicle = new Vehicle();
        Person policyholder = new Person();
        Insurance insurance = new Insurance();

        InsuranceContract insuranceContract = new InsuranceContract.Builder()
                .setPolicyNumber(policyNumber)
                .setDeductible(deductible)
                .setContractDistance(contractDistance)
                .setContractPrice(contractPrice)
                .setBegin(begin)
                .setVehicle(vehicle)
                .setPolicyholder(policyholder)
                .setInsurance(insurance)
                .build();

        assertEquals(policyNumber, insuranceContract.getPolicyNumber());
        assertEquals(deductible, insuranceContract.getDeductible());
        assertEquals(contractDistance, insuranceContract.getContractDistance());
        assertEquals(contractPrice, insuranceContract.getContractPrice());
        assertEquals(begin, insuranceContract.getBegin());
        assertEquals(vehicle, insuranceContract.getVehicle());
        assertEquals(policyholder, insuranceContract.getPolicyholder());
        assertEquals(insurance, insuranceContract.getInsurance());
    }
}
