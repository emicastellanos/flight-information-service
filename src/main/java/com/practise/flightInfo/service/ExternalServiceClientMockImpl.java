package com.practise.flightInfo.service;

import com.practise.flightInfo.mock.MockService;
import com.practise.flightInfo.model.dto.FlightInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExternalServiceClientMockImpl implements ExternalServiceClient {
    @Override
    public List<FlightInfoDTO> getFlightsInformationByTailNumber(final String tailNumber) {
        return MockService.getFlightByTailNumber(tailNumber);
    }
}
