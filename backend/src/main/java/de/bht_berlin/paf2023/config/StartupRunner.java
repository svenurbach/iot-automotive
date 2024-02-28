package de.bht_berlin.paf2023.config;

import de.bht_berlin.paf2023.component.HelloComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
public class StartupRunner implements ApplicationRunner {

  @Autowired
  private HelloComponent helloComponent1;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("Hello PaF 2023");

    System.out.println(helloComponent1.getHello());
  }


}
