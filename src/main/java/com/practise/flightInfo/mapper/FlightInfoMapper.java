package com.practise.flightInfo.mapper;

import com.practise.flightInfo.model.dto.AirportDTO;
import com.practise.flightInfo.model.dto.FlightInfoDTO;
import com.practise.flightInfo.model.entity.Airport;
import com.practise.flightInfo.model.entity.FlightInfo;
import org.springframework.beans.BeanUtils;

public class FlightInfoMapper {

    public static FlightInfo makeFlightInfoEntityFromDTO(FlightInfoDTO source){
        FlightInfo flightInfo = new FlightInfo();
        Airport airportOrigin = new Airport();
        Airport airportDestination = new Airport();

        BeanUtils.copyProperties(source.getOrigin(), airportOrigin);
        BeanUtils.copyProperties(source.getDestination(), airportDestination);
        BeanUtils.copyProperties(source, flightInfo);
        flightInfo.setOrigin(airportOrigin);
        flightInfo.setDestination(airportDestination);

        return flightInfo;
    }

    public static FlightInfoDTO makeFlightInfoDtoFromEntity(FlightInfo source){
        FlightInfoDTO flightInfoDTO = new FlightInfoDTO();
        AirportDTO airportDTOOrigin = new AirportDTO();
        AirportDTO airportDTODestination = new AirportDTO();

        BeanUtils.copyProperties(source.getOrigin(), airportDTOOrigin);
        BeanUtils.copyProperties(source.getDestination(), airportDTODestination);
        BeanUtils.copyProperties(source, flightInfoDTO);
        flightInfoDTO.setOrigin(airportDTOOrigin);
        flightInfoDTO.setDestination(airportDTODestination);

        return flightInfoDTO;
    }
}
