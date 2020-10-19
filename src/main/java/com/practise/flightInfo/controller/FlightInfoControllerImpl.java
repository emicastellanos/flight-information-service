package com.practise.flightInfo.controller;

import com.practise.flightInfo.model.FlightInfoDTO;
import com.practise.flightInfo.service.FlightInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class FlightInfoControllerImpl implements FlightInfoController {
    private static final Logger logger = LoggerFactory.getLogger(FlightInfoControllerImpl.class);

    @Autowired
    private FlightInfoService flightInfoService;

    @Override
    public ResponseEntity<FlightInfoDTO> obtainFlightInfo(String tailNumber, String flightNumber) {
        logger.info("obtain flight info");
        Optional<FlightInfoDTO> flight = flightInfoService.obtainFlightInfo(tailNumber, flightNumber);

        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(flight.get());
    }

    @Override
    public ResponseEntity<List<FlightInfoDTO>> getAll(){
        logger.info("get all method");
        List<FlightInfoDTO> flights = flightInfoService.getAll();
        return ResponseEntity.ok(flights);
    }
}
