package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.entity.Insurance;
import de.bht_berlin.paf2023.repo.InsuranceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;

import java.util.Locale;

@Service
public class InsuranceService {

    private final InsuranceRepo repository;
    private final Faker faker;

    @Autowired
    public InsuranceService(InsuranceRepo repository) {
        this.repository = repository;
        this.faker = new Faker(Locale.getDefault());
    }

    public void generateDummyData(int numberOfEntries) {
        for (int i = 0; i < numberOfEntries; i++) {
            Insurance insurance = new Insurance();
            insurance.setName(faker.name().fullName());
//            insurance.setEmail(faker.internet().emailAddress());
            repository.save(insurance);
        }
    }
}