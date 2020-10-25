package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;

import java.util.List;

public interface ExternalServiceClient {

    List<FlightInfoDTO> getFlightsInformationByTailNumber(final String tailNumber);

}
