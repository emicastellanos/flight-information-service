package com.practise.flightInfo.mock;

import com.practise.flightInfo.model.dto.AirportDTO;
import com.practise.flightInfo.model.dto.FlightInfoDTO;

import java.util.Arrays;
import java.util.List;

public class MockService {
    public static List<FlightInfoDTO> getFlightByTailNumber(String tailNumber) {
        AirportDTO airportOriginOne = new AirportDTO();
        airportOriginOne.setCode("GCXO");
        airportOriginOne.setCity("Tenerife");
        airportOriginOne.setAlternateIdent("TFN");
        airportOriginOne.setAirportName("Tenerife North (Los Rodeos)");
        AirportDTO airportDestinationOne = new AirportDTO();
        airportDestinationOne.setCode("GCGM");
        airportDestinationOne.setCity("La Gomera");
        airportDestinationOne.setAlternateIdent("GMZ");
        airportDestinationOne.setAirportName("La Gomera");

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
        flightInfoEntity.setOrigin(airportOriginOne);
        flightInfoEntity.setDestination(airportDestinationOne);

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
        flightInfoEntity2.setOrigin(airportOriginOne);
        flightInfoEntity2.setDestination(airportDestinationOne);


        return Arrays.asList(flightInfoEntity, flightInfoEntity2);
    }
}
