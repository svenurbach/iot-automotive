package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.component.HelloComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class HelloRestController {

//    @Autowired
//    private HelloComponent helloComponent;

    @RequestMapping(path = "/paf2023")
    public String getHello() {
        return "Hello PaF";
    }
}
