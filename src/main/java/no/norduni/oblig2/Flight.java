/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.time.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

/**
 *
 * @author mortenj
 */
public class Flight {
    private SimpleStringProperty        flightNummer;
    private SimpleStringProperty        origin;
    private SimpleStringProperty        destination;
    private ZonedDateTime               departureTime;
    private Duration                    duration;
    private SimpleIntegerProperty       antallPlasser;
    private ObservableList<Reisende>    reisende;
    private ObservableList<Gruppe>      grupper;

    public Flight() {
        this.flightNummer = new SimpleStringProperty("");
        this.origin = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.departureTime = departureTime; // Simle Object Property
        this.duration = duration;
        this.antallPlasser = new SimpleIntegerProperty(0);
        this.reisende = javafx.collections.FXCollections.observableArrayList();
        this.grupper =  javafx.collections.FXCollections.observableArrayList();
    }

    public String getFlightNummer() {
        return flightNummer.getValue();
    }

    public void setFlightNummer(String flightNummer) {
        this.flightNummer.setValue(flightNummer);
    }

    public SimpleStringProperty flightNummerProperty() {
        return this.flightNummer;
    }

    public String getOrigin() {
        return origin.getValue();
    }

    public void setOrigin(String origin) {
        this.origin.setValue(origin);
    }

    public SimpleStringProperty originProperty() {
        return this.origin;
    }

    public String getDestination() {
        return destination.getValue();
    }

    public void setDestination(String destination) {
        this.destination.setValue(destination);
    }

    public SimpleStringProperty destinationProperty() {
        return this.destination;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public ZonedDateTime departureTimeProperty() {
        return this.departureTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
     public Duration durationProperty() {
        return this.duration;
    }

    public Integer getAntallPlasser() {
        return this.antallPlasser.getValue();
    }
    
    public void setAntallPlasser(Integer a) {
        this.antallPlasser.setValue(a);
    }
    
    public SimpleIntegerProperty antallPlasserProperty() {
           return this.antallPlasser;
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
    
    public ObservableList<Gruppe> getGrupper() {
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

    public void setGrupper(ObservableList<Gruppe> grupper) {
        this.grupper = grupper;
    }
  
    public void addGruppe(Gruppe g) {
        this.grupper.add(g);
    }
}
