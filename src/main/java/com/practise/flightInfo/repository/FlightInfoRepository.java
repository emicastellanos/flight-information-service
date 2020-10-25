package com.practise.flightInfo.repository;

import com.practise.flightInfo.model.entity.FlightInfo;

import java.util.List;
import java.util.Optional;

/**
 * Database Access Object for Flight Information.
 * <p/>
 */
public interface FlightInfoRepository {

    void save(String key, FlightInfo flightInfo);

    List<FlightInfo> getAll();

    Optional<FlightInfo> getFlightByKey(String key);
}
