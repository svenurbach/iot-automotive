package de.bht_berlin.paf2023;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableScheduling
@SpringBootApplication
public class Paf2023Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Paf2023Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
