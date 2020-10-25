package com.practise.flightInfo.model.entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@RedisHash("FlightInformation")
public class FlightInfo implements Serializable {
    private String ident;
    private String faFlightID;
    private String airline;
    private String airlineIata;
    private String flightNumber;
    private String tailNumber;
    private String type;
    private String codeshares;
    private Boolean blocked;
    private Boolean diverted;
    private Boolean cancelled;
    private Airport origin;
    private Airport destination;

    public FlightInfo() {
    }

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

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightInfo)) return false;
        FlightInfo that = (FlightInfo) o;
        return Objects.equals(getIdent(), that.getIdent()) &&
                Objects.equals(getFaFlightID(), that.getFaFlightID()) &&
                Objects.equals(getAirline(), that.getAirline()) &&
                Objects.equals(getAirlineIata(), that.getAirlineIata()) &&
                Objects.equals(getFlightNumber(), that.getFlightNumber()) &&
                Objects.equals(getTailNumber(), that.getTailNumber()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getCodeshares(), that.getCodeshares()) &&
                Objects.equals(getBlocked(), that.getBlocked()) &&
                Objects.equals(getDiverted(), that.getDiverted()) &&
                Objects.equals(getCancelled(), that.getCancelled()) &&
                Objects.equals(getOrigin(), that.getOrigin()) &&
                Objects.equals(getDestination(), that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdent(), getFaFlightID(), getAirline(), getAirlineIata(), getFlightNumber(), getTailNumber(), getType(), getCodeshares(), getBlocked(), getDiverted(), getCancelled(), getOrigin(), getDestination());
    }
}
