/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class Reisende extends ModelBase {

    private BooleanProperty shavenBalls = new SimpleBooleanProperty();
    private StringProperty navn = new SimpleStringProperty();
    private IntegerProperty alder = new SimpleIntegerProperty();
    private StringProperty passnr = new SimpleStringProperty();
    private ObjectProperty<Kjonn> kjonn = new SimpleObjectProperty<>();
    private ObjectProperty<Betaling> betaling = new SimpleObjectProperty<>();
    private ObjectProperty<Gruppe> gruppe = new SimpleObjectProperty<>();

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

    public Gruppe getGruppe() {
        return gruppe.get();
    }

    public void setGruppe(Gruppe value) {
        gruppe.set(value);
        gruppe.getValue().addReisende(this);
    }

    public ObjectProperty gruppeProperty() {
        return gruppe;
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
    
     // Custom serialization. Cannot serialize JavaFX properties. We just serialize the contents.
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeBoolean(this.shavenBalls.get());
        oos.writeObject(this.navn.get());
        oos.writeInt(this.alder.get());
        oos.writeObject(this.passnr.get());
        oos.writeObject(this.kjonn.get());
        oos.writeObject(this.betaling.get());
        oos.writeObject(this.gruppe.get());
    }

    // Custom unserialization. Put values into their respective property wrappers.
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        this.shavenBalls    = UnserializePropertyFactory.magic(ois.readBoolean());
        this.navn           = UnserializePropertyFactory.magic((String) ois.readObject());
        this.alder          = UnserializePropertyFactory.magic(ois.readInt());
        this.passnr         = UnserializePropertyFactory.magic((String) ois.readObject());
        this.kjonn          = UnserializePropertyFactory.magic((Kjonn) ois.readObject());
        this.betaling       = UnserializePropertyFactory.magic((Betaling) ois.readObject());
        this.gruppe         = UnserializePropertyFactory.magic((Gruppe) ois.readObject());
    }
    
}
