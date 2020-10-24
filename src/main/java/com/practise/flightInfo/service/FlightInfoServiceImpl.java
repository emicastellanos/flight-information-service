package com.practise.flightInfo.service;

import com.practise.flightInfo.model.FlightInfoDTO;
import com.practise.flightInfo.repository.FlightInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FlightInfoServiceImpl implements FlightInfoService {
    private static final String KEY_SEPARATOR = "-";
    private static final Logger logger = LoggerFactory.getLogger(FlightInfoServiceImpl.class);

    private final FlightInfoRepository flightInformationRepositoy;
    private final ExternalServiceClient externalServiceClient;

    @Autowired
    public FlightInfoServiceImpl(final FlightInfoRepository flightInformationRepositoy,
                                 final ExternalServiceClient externalServiceClient) {
        this.flightInformationRepositoy = flightInformationRepositoy;
        this.externalServiceClient = externalServiceClient;
    }

    @Override
    public void save(final String key, final FlightInfoDTO flightInfoDTO) {
        flightInformationRepositoy.save(key, flightInfoDTO);
    }

    @Override
    public Optional<FlightInfoDTO> getByTailNumberAndFlightNumber(String tailNumber, String flightNumber) {
        String key = generateKey(tailNumber, flightNumber);
        logger.info("looking in cache key:" + key);
        Optional<FlightInfoDTO> flight = flightInformationRepositoy.getFlightByKey(key);

        if (flight.isPresent()) {
            return flight;
        }
        logger.info("not found:" + key);
        Optional<FlightInfoDTO> flightFromService = getFlightInfoFromExternalService(tailNumber, flightNumber);

        if (flightFromService.isPresent()) {
            save(key, flightFromService.get());
        }
        return flightFromService;
    }

    @Override
    public List<FlightInfoDTO> getAll() {
        return flightInformationRepositoy.getAll();
    }


    private String generateKey(final String tailNumber, final String flightNumber) {
        return tailNumber + KEY_SEPARATOR + flightNumber;
    }

    private Optional<FlightInfoDTO> getFlightInfoFromExternalService(final String tailNumber, final String flightNumber) {
        List<FlightInfoDTO> flightsResponse = externalServiceClient.getFlightsInformationByTailNumber(tailNumber);

        return Optional.ofNullable(flightsResponse)
                .map(Collection::parallelStream)
                .orElse(Stream.empty())
                .filter(flightResponse -> flightResponse.getFlightNumber().equals(flightNumber))
                .findFirst();
    }
}
