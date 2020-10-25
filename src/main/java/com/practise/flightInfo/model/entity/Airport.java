package com.practise.flightInfo.model.entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@RedisHash("Airport")
public class Airport implements Serializable {
    private String code;
    private String city;
    private String alternateIdent;
    private String airportName;

    public Airport() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport that = (Airport) o;
        return Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getAlternateIdent(), that.getAlternateIdent()) &&
                Objects.equals(getAirportName(), that.getAirportName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getCity(), getAlternateIdent(), getAirportName());
    }
}
