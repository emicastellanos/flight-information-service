package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;

import java.util.List;

/**
 * Service responsible for obtaining flight information from external service.
 * <p/>
 */
public interface ExternalServiceClient {

    List<FlightInfoDTO> getFlightsInformationByTailNumber(final String tailNumber);

}
