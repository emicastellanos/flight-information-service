package com.practise.flightInfo.service;

import com.practise.flightInfo.model.FlightInfoDTO;

import java.util.List;
import java.util.Optional;

public interface FlightInfoService {

    void saveFlightInfo(String key, FlightInfoDTO flightInfoDTO);

    Optional<FlightInfoDTO> obtainFlightInfo(String tailNumber, String flightNumber);

    List<FlightInfoDTO> getAll();

}
