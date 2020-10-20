package com.practise.flightInfo.repository;

import com.practise.flightInfo.model.FlightInfoDTO;

import java.util.List;
import java.util.Optional;

public interface FlightInfoRepository {

    void save(String key, FlightInfoDTO flightInfoDTO);

    List<FlightInfoDTO> getAll();

    Optional<FlightInfoDTO> getFlightByKey(String key);
}
