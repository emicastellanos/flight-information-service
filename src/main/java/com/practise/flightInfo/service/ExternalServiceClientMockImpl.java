package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ExternalServiceClientMockImpl implements ExternalServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceClientMockImpl.class);
    private final String EXTERNAL_URL_FORMAT = "http://mock-server:1080/v1/flight-information-tail/%s";

    @Override
    public List<FlightInfoDTO> getFlightsInformationByTailNumber(final String tailNumber) {
        logger.info("external service: preparing to call external service for: " + tailNumber);
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(EXTERNAL_URL_FORMAT, tailNumber);
        ResponseEntity<List<FlightInfoDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightInfoDTO>>(){});
        List<FlightInfoDTO> flightsResponse = response.getBody();
        flightsResponse.forEach(f -> logger.info(f.getFlightNumber()));

        return flightsResponse;
    }
}
