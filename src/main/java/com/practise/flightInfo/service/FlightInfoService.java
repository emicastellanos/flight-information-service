package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import com.practise.flightInfo.model.entity.FlightInfo;

import java.util.List;
import java.util.Optional;

public interface FlightInfoService {

    void save(final String key, final FlightInfo flightInfo);

    Optional<FlightInfoDTO> getByTailNumberAndFlightNumber(final String tailNumber, final String flightNumber);

    List<FlightInfoDTO> getAll();

}
