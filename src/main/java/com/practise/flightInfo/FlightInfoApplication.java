package com.practise.flightInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlightInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightInfoApplication.class, args);
    }

}
