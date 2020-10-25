package com.practise.flightInfo.controller;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "Flight Information Controller", description = "REST API for caching Flights Information")
public interface FlightInfoController {

    @ApiOperation(value = "Information about a given flight is gotten from cache or from external service and saved in cache afterwards, by tail number and flight number",
            httpMethod = "GET",
            response = FlightInfoDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A FlightInfoDTO is obtained correctly"),
            @ApiResponse(code = 404, message = "A FlightInfoDTO was not found neither in chache nor in external service")})
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
