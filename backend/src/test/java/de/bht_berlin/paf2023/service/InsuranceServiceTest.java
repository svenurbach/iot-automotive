package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.InsuranceContract;
import de.bht_berlin.paf2023.entity.Person;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.repo.ContractRepo;
import de.bht_berlin.paf2023.repo.PersonRepo;
import de.bht_berlin.paf2023.repo.VehicleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    private InsuranceService insuranceService;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private PersonRepo personRepo;

    @Mock
    private VehicleRepo vehicleRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        insuranceService = new InsuranceService(contractRepo, personRepo, vehicleRepo);
    }

    @Test
    void getAllContracts() {
        List<InsuranceContract> contractList = new ArrayList<>();
        contractList.add(new InsuranceContract());
        when(contractRepo.findAll()).thenReturn(contractList);
        assertEquals(contractList, insuranceService.getAllContracts());
    }

    @Test
    void getInsuranceContractById() {
        InsuranceContract contract = new InsuranceContract();
        Long id = 1L;
        when(contractRepo.findById(id)).thenReturn(Optional.of(contract));
        assertEquals(contract, insuranceService.getInsuranceContractById(id));
    }

    @Test
    void getInsuranceByCar() {
        InsuranceContract contract = new InsuranceContract();
        Long carId = 1L;

        Vehicle vehicle = new Vehicle();
        vehicle.setId(carId);
        vehicle.setInsuranceContract(contract);

        when(vehicleRepo.findById(carId)).thenReturn(Optional.of(vehicle));

        assertEquals(contract, insuranceService.getInsuranceByCar(carId));
    }

    @Test
    void getInsurancesByPerson() {
        InsuranceContract contract1 = new InsuranceContract();
        InsuranceContract contract2 = new InsuranceContract();
        Long personId = 1L;

        Person policyHolder = new Person();
        policyHolder.setId(personId);
        policyHolder.setInsuranceContracts(List.of(contract1, contract2));

        when(personRepo.findById(personId)).thenReturn(Optional.of(policyHolder));

        List<InsuranceContract> expectedContracts = List.of(contract1, contract2);
        assertEquals(expectedContracts, insuranceService.getInsurancesByPerson(personId));
    }

    @Test
    void addInsuranceContract() {
        InsuranceContract contract = new InsuranceContract();
        when(contractRepo.save(contract)).thenReturn(contract);
        assertEquals(contract, insuranceService.addInsuranceContract(contract));
    }

    @Test
    void deleteInsuranceContract() {
        Long contractId = 1L;
        assertEquals("InsuranceContract #" + contractId + " deleted", insuranceService.deleteInsuranceContract(contractId));
        verify(contractRepo, times(1)).deleteById(contractId);
    }
}