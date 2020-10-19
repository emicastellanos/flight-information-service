package com.practise.flightInfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Airport")
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO implements Serializable {
    @JsonProperty("code")
    private String code;
    @JsonProperty("city")
    private String city;
    @JsonProperty("alternate_ident")
    private String alternateIdent;
    @JsonProperty("airport_name")
    private String airportName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlternateIdent() {
        return alternateIdent;
    }

    public void setAlternateIdent(String alternateIdent) {
        this.alternateIdent = alternateIdent;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
