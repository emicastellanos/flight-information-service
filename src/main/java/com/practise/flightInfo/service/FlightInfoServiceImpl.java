package com.practise.flightInfo.service;

import com.practise.flightInfo.mapper.FlightInfoMapper;
import com.practise.flightInfo.model.dto.FlightInfoDTO;
import com.practise.flightInfo.model.entity.FlightInfo;
import com.practise.flightInfo.repository.FlightInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FlightInfoServiceImpl implements FlightInfoService {
    private static final Logger logger = LoggerFactory.getLogger(FlightInfoServiceImpl.class);
    /**
     * The key for persisting in database
     */
    private static final String KEY_FORMAT = "%s-%s";

    private final FlightInfoRepository flightInformationRepository;
    private final ExternalServiceClient externalServiceClient;

    @Autowired
    public FlightInfoServiceImpl(final FlightInfoRepository flightInformationRepository,
                                 final ExternalServiceClient externalServiceClient) {
        this.flightInformationRepository = flightInformationRepository;
        this.externalServiceClient = externalServiceClient;
    }


    @Override
    public void save(final String key, final FlightInfo flightInfo) {
        flightInformationRepository.save(key, flightInfo);
    }

    @Override
    public Optional<FlightInfoDTO> getByTailNumberAndFlightNumber(String tailNumber, String flightNumber) {
        final String key = generateKey(tailNumber, flightNumber);
        logger.info("service: looking in cache key:" + key);
        Optional<FlightInfo> flightEntity = flightInformationRepository.getFlightByKey(key);

        if (flightEntity.isPresent()) {
            FlightInfoDTO flightInfoDTO = FlightInfoMapper.makeFlightInfoDtoFromEntity(flightEntity.get());
            return Optional.of(flightInfoDTO);
        }

        logger.info("service: not found in cache key:" + key + " searching in external service");
        Optional<FlightInfoDTO> flightFromService = getFlightInfoFromExternalService(tailNumber, flightNumber);

        if (flightFromService.isPresent()) {
            logger.info("service: saving flight info found with key:" + key);
            FlightInfo flightInfo = FlightInfoMapper.makeFlightInfoEntityFromDTO(flightFromService.get());
            save(key, flightInfo);
        }

        return flightFromService;
    }

    @Override
    public List<FlightInfoDTO> getAll() {
        List<FlightInfo> flightInfoList = flightInformationRepository.getAll();
        List<FlightInfoDTO> flightInfoDTOList = new ArrayList<>();
        BeanUtils.copyProperties(flightInfoList, flightInfoDTOList);
        return flightInfoDTOList;
    }


    private String generateKey(final String tailNumber, final String flightNumber) {
        return String.format(KEY_FORMAT, tailNumber, flightNumber);
    }

    /**
     * Searches in external service for a given tailNumber and filters the response by a given flightNumber
     *
     * @param tailNumber
     * @param flightNumber
     *
     * @return Optional<FlightInfoDto>
     */
    private Optional<FlightInfoDTO> getFlightInfoFromExternalService(final String tailNumber, final String flightNumber) {
        List<FlightInfoDTO> flightsResponse = externalServiceClient.getFlightsInformationByTailNumber(tailNumber);

        return Optional.ofNullable(flightsResponse)
                .map(Collection::parallelStream)
                .orElse(Stream.empty())
                .filter(flightResponse -> flightResponse.getFlightNumber().equals(flightNumber))
                .findFirst();
    }
}
