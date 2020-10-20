package com.practise.flightInfo.service;

import com.practise.flightInfo.model.FlightInfoDTO;

import java.util.List;
import java.util.Optional;

public interface FlightInfoService {

    void save(final String key, final FlightInfoDTO flightInfoDTO);

    Optional<FlightInfoDTO> getByTailNumberAndFlightNumber(final String tailNumber, final String flightNumber);

    List<FlightInfoDTO> getAll();

}
