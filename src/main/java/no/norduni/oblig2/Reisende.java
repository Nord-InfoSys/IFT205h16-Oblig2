/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bubbaJ
 */
public class Reisende {

    private final BooleanProperty shavenBalls = new SimpleBooleanProperty();
    private final StringProperty navn = new SimpleStringProperty();
    private final IntegerProperty alder = new SimpleIntegerProperty();
    private final StringProperty passnr = new SimpleStringProperty();
    private final ObjectProperty<Kjonn> kjonn = new SimpleObjectProperty<>();
    private final ObjectProperty<Betaling> betaling = new SimpleObjectProperty<>();

    public Betaling getBetaling() {
        return betaling.get();
    }

    public void setBetaling(Betaling value) {
        betaling.set(value);
    }

    public ObjectProperty betalingProperty() {
        return betaling;
    }
    

    public Kjonn getKjonn() {
        return kjonn.get();
    }

    public void setKjonn(Kjonn value) {
        kjonn.set(value);
    }

    public ObjectProperty kjonnProperty() {
        return kjonn;
    }

    public String getPassnr() {
        return passnr.get();
    }

    public void setPassnr(String value) {
        passnr.set(value);
    }

    public StringProperty passnrProperty() {
        return passnr;
    }

    public int getAlder() {
        return alder.get();
    }

    public void setAlder(int value) {
        alder.set(value);
    }

    public IntegerProperty alderProperty() {
        return alder;
    }

    public String getNavn() {
        return navn.get();
    }

    public void setNavn(String value) {
        navn.set(value);
    }

    public StringProperty navnProperty() {
        return navn;
    }

    public boolean isShavenBalls() {
        return shavenBalls.get();
    }

    public void setShavenBalls(boolean value) {
        shavenBalls.set(value);
    }

    public BooleanProperty shavenBallsProperty() {
        return shavenBalls;
    }
    
}
