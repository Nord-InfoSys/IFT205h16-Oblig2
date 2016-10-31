/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author mortenj
 */
public class Flight {
    String      flightNummer;
    String      origin;
    String      destination;
    ZonedDateTime   departureTime;
    Duration      duration;
    SimpleIntegerProperty         antallPlasser;
    List<Reisende>  reisende;
    List<Gruppe>  grupper;

    public Flight(String flightNumber, String origin, String destination, ZonedDateTime departureTime, Duration duration, Integer antallPlasser) {
        this.flightNummer = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.duration = duration;
        this.antallPlasser = new SimpleIntegerProperty(antallPlasser);
        this.reisende = new ArrayList<Reisende>();
        this.grupper = new ArrayList<Gruppe>();
    }

    public Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, Duration duration, Integer antallPlasser) {
        ZonedDateTime zDepartureTime = ZonedDateTime.of(departureTime, ZoneId.systemDefault());
        
        this.flightNummer = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = zDepartureTime;
        this.duration = duration;
        this.antallPlasser = new SimpleIntegerProperty(antallPlasser);
        this.reisende = new ArrayList<Reisende>();
        this.grupper = new ArrayList<Gruppe>();
    }

    public String getFlightNummer() {
        return flightNummer;
    }

    public void setFlightNummer(String flightNummer) {
        this.flightNummer = flightNummer;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    public SimpleIntegerProperty antallPlasserProperty() {
           return this.antallPlasser;
    }
    
    public Integer getAntallPlasser() {
        return this.antallPlasser.getValue();
    }
    
    public void setAntallPlasser(Integer a) {
        this.antallPlasser.setValue(a);
    }
    
    public ZonedDateTime getArrivalTime() {
        return this.getDepartureTime().plus(this.getDuration());
    }
    
    public void addReisende(Reisende passasjer) {
        this.reisende.add(passasjer);
    }
    
    public int getAntallBooket() {
        return this.reisende.size();
    }

    public List<Reisende> getReisende() {
        return this.reisende;
    }

    public Reisende getReisendeByIndex(Integer index) {
        return this.reisende.get(index);
    }

    public Set<String> getReisendeIndexSet() {
        TreeSet<String> ret = new TreeSet();
        for(Reisende r : reisende) {
            ret.add(String.valueOf(reisende.lastIndexOf(r)));
        }
        return ret;
    }
    
    public List<Gruppe> getGrupper() {
        return grupper;
    }

    public Gruppe getGruppeByIndex(Integer index) {
        return this.grupper.get(index);
    }
 
    public Set<String> getGruppeIndexSet() {
        TreeSet<String> ret = new TreeSet();
        for(Gruppe r : grupper) {
            ret.add(String.valueOf(grupper.lastIndexOf(r)));
        }
        return ret;
    }

    public void setGrupper(List<Gruppe> grupper) {
        this.grupper = grupper;
    }
  
    public void addGruppe(Gruppe g) {
        this.grupper.add(g);
    }
}
