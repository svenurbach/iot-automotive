package de.bht_berlin.paf2023.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class HelloRestController {

    @RequestMapping(path = "/paf2023")
    public String getHello() {
        return "Hello PaF";
    }

}
