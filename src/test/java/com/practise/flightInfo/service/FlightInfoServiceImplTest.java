package com.practise.flightInfo.service;

import com.practise.flightInfo.model.dto.FlightInfoDTO;
import com.practise.flightInfo.model.entity.FlightInfo;
import com.practise.flightInfo.repository.FlightInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FlightInfoServiceImplTest {
    private static final String TAIL_NUMBER = "tailNumber";
    private static final String FLIGHT_NUMBER = "flightNumber";
    private static final String KEY = TAIL_NUMBER + "-" + FLIGHT_NUMBER;

    @Mock
    private FlightInfoRepository flightInfoRepository;

    @Mock
    private ExternalServiceClient externalServiceClient;

    @InjectMocks
    private FlightInfoServiceImpl flightInfoService;

    @Test
    public void shouldSaveFlightInfo() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoToBeSaved = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoToBeSaved);

        //when
        flightInfoService.save(KEY, flightInfoToBeSaved);

        //then
        verify(flightInfoRepository).save(KEY, flightInfoToBeSaved);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumber_WhenIsPresentInCache() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoFromCache = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoFromCache);

        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoFromCache));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoDto);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberNotCallExternalService_WhenIsPresentInCache() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoFromCache = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoFromCache);
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoFromCache));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(externalServiceClient, times(0)).getFlightsInformationByTailNumber(anyString());
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberDoNotSave_WhenIsAlreadyPresentInCache() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoFromCache = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoFromCache);
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoFromCache));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(flightInfoRepository, times(0)).save(anyString(), any(FlightInfo.class));
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberGetInfoFromExternalService_WhenIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoEntity = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoEntity));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(externalServiceClient, times(1)).getFlightsInformationByTailNumber(TAIL_NUMBER);
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoEntity);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberGetInfoCorrectlyFilteredFromExternalService_WhenIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoEntity = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(
                        Arrays.asList(
                                getMinimalFlightInfo(TAIL_NUMBER, "123"),
                                getMinimalFlightInfo(TAIL_NUMBER, "flightNumber2"),
                                flightInfoEntity,
                                getMinimalFlightInfo(TAIL_NUMBER, "anotherOne")));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(externalServiceClient, times(1)).getFlightsInformationByTailNumber(TAIL_NUMBER);
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoEntity);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberSaveInCache_WhenFlightIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoToBeSaved = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoToBeSaved);

        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoDto));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(flightInfoRepository, times(1)).save(KEY, flightInfoToBeSaved);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberReturnOptionalEmpty_WhenIsNotInCacheNorExternalService() {
        //given
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Collections.emptyList());

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (!serviceResponse.isPresent());
        Assertions.assertThat(serviceResponse).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberQueryExternalService_WhenIsNotInCache() {
        //given
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Collections.emptyList());

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(flightInfoRepository, times(1)).getFlightByKey(KEY);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberSaveInCache_WhenFlightInfoIsGottenFromExternalService() {
        //given
        FlightInfoDTO flightInfoDto = getOneFlightInfoDto();
        FlightInfo flightInfoToBeSaved = new FlightInfo();
        BeanUtils.copyProperties(flightInfoDto, flightInfoToBeSaved);
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoDto));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(flightInfoRepository, times(1)).save(KEY, flightInfoToBeSaved);
    }


    private FlightInfoDTO getOneFlightInfoDto() {
        FlightInfoDTO flightInfoEntity = new FlightInfoDTO();
        flightInfoEntity.setIdent("IBB653");
        flightInfoEntity.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoEntity.setAirlineIata("NT");
        flightInfoEntity.setAirline("IBB");
        flightInfoEntity.setTailNumber(TAIL_NUMBER);
        flightInfoEntity.setFlightNumber(FLIGHT_NUMBER);
        flightInfoEntity.setType("Form_Airline");
        flightInfoEntity.setCodeshares("IBE123");
        flightInfoEntity.setBlocked(false);
        flightInfoEntity.setCancelled(false);
        flightInfoEntity.setDiverted(false);
        return flightInfoEntity;
    }

    private FlightInfoDTO getMinimalFlightInfo(String tailNumber, String flightNumber) {
        FlightInfoDTO flightInfoEntity = new FlightInfoDTO();
        flightInfoEntity.setTailNumber(tailNumber);
        flightInfoEntity.setFlightNumber(flightNumber);
        return flightInfoEntity;
    }


}