package de.bht_berlin.paf2023;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Paf2023ApplicationIntegrationTest {

  @Autowired
  private Paf2023Application paf2023Application;

	@Test
	void contextLoads() {
    System.out.println("To string:" + paf2023Application.toString());
	}

}
