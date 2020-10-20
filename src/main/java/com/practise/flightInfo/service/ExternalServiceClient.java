package com.practise.flightInfo.service;

import com.practise.flightInfo.model.FlightInfoDTO;

import java.util.List;

public interface ExternalServiceClient {

    List<FlightInfoDTO> getFlightsInformationByTailNumber(final String tailNumber);

}
