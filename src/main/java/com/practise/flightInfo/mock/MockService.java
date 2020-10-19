package com.practise.flightInfo.mock;

import com.practise.flightInfo.model.FlightInfoDTO;

import java.util.Arrays;
import java.util.List;

public class MockService {
    public static List<FlightInfoDTO> getFlightByTailNumber(String tailNumber) {
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        flightInfoDTO.setIdent("IBB653");
        flightInfoDTO.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoDTO.setAirlineIata("NT");
        flightInfoDTO.setAirline("IBB");
        flightInfoDTO.setTailNumber("EC-MYT");
        flightInfoDTO.setFlightNumber("653");
        flightInfoDTO.setType("Form_Airline");
        flightInfoDTO.setCodeshares("IBE123");
        flightInfoDTO.setBlocked(false);
        flightInfoDTO.setCancelled(false);
        flightInfoDTO.setDiverted(false);

        FlightInfoDTO flightInfoDTO2 = new FlightInfoDTO();
        flightInfoDTO2.setIdent("IBB653");
        flightInfoDTO2.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoDTO2.setAirlineIata("NT");
        flightInfoDTO2.setAirline("IBB");
        flightInfoDTO2.setTailNumber("EC-MYT");
        flightInfoDTO2.setFlightNumber("123");
        flightInfoDTO2.setType("Form_Airline");
        flightInfoDTO2.setCodeshares("IBE123");
        flightInfoDTO2.setBlocked(false);
        flightInfoDTO2.setCancelled(false);
        flightInfoDTO2.setDiverted(false);


        return Arrays.asList(flightInfoDTO, flightInfoDTO2);
    }
}
