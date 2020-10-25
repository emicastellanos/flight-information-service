package com.practise.flightInfo.controller;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface FlightInfoController {

    @RequestMapping(value = "flight-information/{tail-number}/{flight-number}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<FlightInfoDTO> obtainFlightInfo(@PathVariable("tail-number") String tailNumber,
                                                   @PathVariable("flight-number") String flightNumber);

    @RequestMapping(value = "get-all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<FlightInfoDTO>> getAll();
}
