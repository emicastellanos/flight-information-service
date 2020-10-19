package com.practise.flightInfo.service;

import com.practise.flightInfo.mock.MockService;
import com.practise.flightInfo.model.FlightInfoDTO;
import com.practise.flightInfo.repository.FlightInfoRepositoy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightInfoServiceImpl implements FlightInfoService {
    private static final String KEY_SEPARATOR = "-";
    private static final Logger logger = LoggerFactory.getLogger(FlightInfoServiceImpl.class);

    @Autowired
    private FlightInfoRepositoy flightInformationRepositoy;


    @Override
    public void saveFlightInfo(String key, FlightInfoDTO flightInfoDTO) {
        flightInformationRepositoy.save(key, flightInfoDTO);
    }

    @Override
    public Optional<FlightInfoDTO> obtainFlightInfo(final String tailNumber, final String flightNumber) {
        String key = generateKey(tailNumber, flightNumber);
        logger.info("looking in cache key:" + key);
        Optional<FlightInfoDTO> flight = flightInformationRepositoy.getFlightByKey(key);

        if (flight.isPresent()) {
            return flight;
        }
        logger.info("not found:" + key);
        Optional<FlightInfoDTO> flightResponse = getFromExternalService(tailNumber, flightNumber);
        if (flightResponse.isPresent()) {
            saveFlightInfo(key, flightResponse.get());
        }

        return flightResponse;
    }

    @Override
    public List<FlightInfoDTO> getAll() {
        return flightInformationRepositoy.getAll();
    }

    private String generateKey(final String tailNumber, final String flightNumber) {
        return tailNumber + KEY_SEPARATOR + flightNumber;
    }

    private Optional<FlightInfoDTO> getFromExternalService(final String tailNumber, final String flightNumber) {
        logger.info("looking in external service");
        List<FlightInfoDTO> flightsResponse = MockService.getFlightByTailNumber(tailNumber);
        return flightsResponse.stream()
                .filter(flightResponse -> flightResponse.getFlightNumber().equals(flightNumber))
                .findFirst();
    }

}
