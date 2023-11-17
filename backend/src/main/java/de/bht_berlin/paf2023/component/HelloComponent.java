package de.bht_berlin.paf2023.component;

import org.springframework.stereotype.Component;

public class HelloComponent {
    private static Integer callCounter = 0;

    private Integer InstanceId;

//    public HelloComponent() {
//        System.out.println("HelloComponent constructor called");
//    }

    public String getHello() {
        String id = Integer.toHexString(System.identityHashCode(this));
        return "Hello PaF 2023 from Component" + ++callCounter + " instance ID:" + InstanceId + ", " + this.hashCode() + id;
    }

}
