package com.practise.flightInfo.controller;


import com.practise.flightInfo.model.FlightInfoDTO;
import com.practise.flightInfo.service.FlightInfoService;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = FlightInfoControllerImpl.class)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FlightInfoControllerImplTest {
    private static final String TAIL_NUMBER = "tailNumber";
    private static final String FLIGHT_NUMBER = "flightNumber";
    private static final String PATH = "/v1/flight-information/%s/%s";

    @MockBean
    private FlightInfoService flightInfoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void obtainFlightInfo() {
        //given
        String validPath = String.format(PATH, TAIL_NUMBER, FLIGHT_NUMBER);
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        when(flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER))
                .thenReturn(Optional.of(flightInfoDTO));

        //when
        given().mockMvc(mockMvc)
                .when()
                .get(validPath)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract().body().as(FlightInfoDTO.class).equals(flightInfoDTO);
    }

    @Test
    public void obtainFlightInfoEmpty_WhenIsNotFound() {
        //given
        String validPath = String.format(PATH, TAIL_NUMBER, FLIGHT_NUMBER);
        when(flightInfoService.getByTailNumberAndFlightNumber(TAIL_NUMBER, FLIGHT_NUMBER))
                .thenReturn(Optional.empty());

        //when
        given().mockMvc(mockMvc)
                .when()
                .get(validPath)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}