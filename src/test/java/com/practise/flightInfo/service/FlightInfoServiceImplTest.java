package com.practise.flightInfo.service;

import com.practise.flightInfo.model.FlightInfoDTO;
import com.practise.flightInfo.repository.FlightInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();

        //when
        flightInfoService.save(KEY, flightInfoDTO);

        //then
        verify(flightInfoRepository).save(KEY, flightInfoDTO);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumber_WhenIsPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoDTO));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoDTO);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberNotCallExternalService_WhenIsPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoDTO));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(externalServiceClient, times(0)).getFlightsInformationByTailNumber(anyString());
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberDoNotSave_WhenIsAlreadyPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.of(flightInfoDTO));
        //when
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(flightInfoRepository, times(0)).save(anyString(), any(FlightInfoDTO.class));
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberGetInfoFromExternalService_WhenIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoDTO));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(externalServiceClient, times(1)).getFlightsInformationByTailNumber(TAIL_NUMBER);
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoDTO);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberGetInfoCorrectlyFilteredFromExternalService_WhenIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(
                        Arrays.asList(
                                getMinimalFlightInfo(TAIL_NUMBER, "123"),
                                getMinimalFlightInfo(TAIL_NUMBER, "flightNumber2"),
                                flightInfoDTO,
                                getMinimalFlightInfo(TAIL_NUMBER, "anotherOne")));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(externalServiceClient, times(1)).getFlightsInformationByTailNumber(TAIL_NUMBER);
        Assertions.assertThat(serviceResponse.get()).isEqualTo(flightInfoDTO);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberSaveInCache_WhenFlightIsNotPresentInCache() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoDTO));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        assert (serviceResponse.isPresent());
        verify(flightInfoRepository, times(1)).save(KEY, flightInfoDTO);
    }

    @Test
    public void shouldGetFlightInfoByTailAndFlightNumberReturnOptionalEmpty_WhenIsNotInCacheNorExternalService() {
        //given
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
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
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
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
        FlightInfoDTO flightInfoDTO = getOneFlightInfoDto();
        given(flightInfoRepository.getFlightByKey(KEY))
                .willReturn(Optional.empty());
        given(externalServiceClient.getFlightsInformationByTailNumber(TAIL_NUMBER))
                .willReturn(Arrays.asList(flightInfoDTO));

        //When
        Optional<FlightInfoDTO> serviceResponse =
                flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER);
        //then
        verify(flightInfoRepository, times(1)).save(KEY, flightInfoDTO);
    }


    private FlightInfoDTO getOneFlightInfoDto() {
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        flightInfoDTO.setIdent("IBB653");
        flightInfoDTO.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoDTO.setAirlineIata("NT");
        flightInfoDTO.setAirline("IBB");
        flightInfoDTO.setTailNumber(TAIL_NUMBER);
        flightInfoDTO.setFlightNumber(FLIGHT_NUMBER);
        flightInfoDTO.setType("Form_Airline");
        flightInfoDTO.setCodeshares("IBE123");
        flightInfoDTO.setBlocked(false);
        flightInfoDTO.setCancelled(false);
        flightInfoDTO.setDiverted(false);
        return flightInfoDTO;
    }

    private FlightInfoDTO getMinimalFlightInfo(String tailNumber, String flightNumber){
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        flightInfoDTO.setTailNumber(tailNumber);
        flightInfoDTO.setFlightNumber(flightNumber);
        return flightInfoDTO;
    }


}