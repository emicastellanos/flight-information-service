package com.practise.flightInfo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("FlightInformation")
//@Data
@NoArgsConstructor
public class FlightInfoDTO implements Serializable {
    @JsonProperty("ident")
    private String ident;
    @JsonProperty("faFlightID")
    private String faFlightID;
    @JsonProperty("airline")
    private String airline;
    @JsonProperty("airline_iata")
    private String airlineIata;
    @JsonProperty("flightnumber")
    private String flightNumber;
    @JsonProperty("tailnumber")
    private String tailNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("codeshares")
    private String codeshares;
    @JsonProperty("blocked")
    private Boolean blocked;
    @JsonProperty("diverted")
    private Boolean diverted;
    @JsonProperty("cancelled")
    private Boolean cancelled;
    @JsonProperty("origin")
    private AirportDTO origin;
    @JsonProperty("destination")
    private AirportDTO destination;

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getFaFlightID() {
        return faFlightID;
    }

    public void setFaFlightID(String faFlightID) {
        this.faFlightID = faFlightID;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeshares() {
        return codeshares;
    }

    public void setCodeshares(String codeshares) {
        this.codeshares = codeshares;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getDiverted() {
        return diverted;
    }

    public void setDiverted(Boolean diverted) {
        this.diverted = diverted;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public AirportDTO getOrigin() {
        return origin;
    }

    public void setOrigin(AirportDTO origin) {
        this.origin = origin;
    }

    public AirportDTO getDestination() {
        return destination;
    }

    public void setDestination(AirportDTO destination) {
        this.destination = destination;
    }
}
