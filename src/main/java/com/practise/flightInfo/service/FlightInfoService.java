package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import com.practise.flightInfo.model.entity.FlightInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for all business logic:
 * querying, communicating with external service and persisting flight information.
 * <p/>
 */
public interface FlightInfoService {

    /**
     * Persist in data base a pair key - FlightInfo
     *
     * @param key
     * @param flightInfo to be persisted
     */
    void save(final String key, final FlightInfo flightInfo);

    /**
     * Searches for a combination of tailNumber and flightNumber
     *
     * @param tailNumber
     * @param flightNumber
     *
     * @return Optional<FlightInfoDto>
     */
    Optional<FlightInfoDTO> getByTailNumberAndFlightNumber(final String tailNumber, final String flightNumber);

    List<FlightInfoDTO> getAll();

}
