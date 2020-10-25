package com.practise.flightInfo.mock;

import com.practise.flightInfo.model.dto.FlightInfoDTO;

import java.util.Arrays;
import java.util.List;

public class MockService {
    public static List<FlightInfoDTO> getFlightByTailNumber(String tailNumber) {
        FlightInfoDTO flightInfoEntity = new FlightInfoDTO();
        flightInfoEntity.setIdent("IBB653");
        flightInfoEntity.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoEntity.setAirlineIata("NT");
        flightInfoEntity.setAirline("IBB");
        flightInfoEntity.setTailNumber("EC-MYT");
        flightInfoEntity.setFlightNumber("653");
        flightInfoEntity.setType("Form_Airline");
        flightInfoEntity.setCodeshares("IBE123");
        flightInfoEntity.setBlocked(false);
        flightInfoEntity.setCancelled(false);
        flightInfoEntity.setDiverted(false);

        FlightInfoDTO flightInfoEntity2 = new FlightInfoDTO();
        flightInfoEntity2.setIdent("IBB653");
        flightInfoEntity2.setFaFlightID("IBB653-1581399936-airline-0136");
        flightInfoEntity2.setAirlineIata("NT");
        flightInfoEntity2.setAirline("IBB");
        flightInfoEntity2.setTailNumber("EC-MYT");
        flightInfoEntity2.setFlightNumber("123");
        flightInfoEntity2.setType("Form_Airline");
        flightInfoEntity2.setCodeshares("IBE123");
        flightInfoEntity2.setBlocked(false);
        flightInfoEntity2.setCancelled(false);
        flightInfoEntity2.setDiverted(false);


        return Arrays.asList(flightInfoEntity, flightInfoEntity2);
    }
}
