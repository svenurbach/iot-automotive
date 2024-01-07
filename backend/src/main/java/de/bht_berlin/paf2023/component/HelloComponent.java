package de.bht_berlin.paf2023.component;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@Slf4j
public class HelloComponent {

    private static final Logger logger = LoggerFactory.getLogger(HelloComponent.class);
    private static Integer callCounter = 0;

    private Integer InstanceId;

//    public HelloComponent() {
//        System.out.println("HelloComponent constructor called");
//    }

    public String getHello() {
//        String id = Integer.toHexString(System.identityHashCode(this));
//        logger.info("bla");
//        return "Hello PaF 2023 from Component" + ++callCounter + " instance ID:" + InstanceId + ", " + this.hashCode() + id;
        return "Hello PaF 2023 from Component";
    }

}
