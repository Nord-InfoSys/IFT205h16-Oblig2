/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author bubbaJ
 */
public class Gruppe extends ModelBase {
    private SimpleStringProperty      gruppeKode;
    private ObservableList<Reisende>  reisende;

    public Gruppe() {
        this.gruppeKode = new SimpleStringProperty("");
        this.reisende = javafx.collections.FXCollections.observableArrayList();
    }

    public String getGruppeKode() {
        return gruppeKode.getValue();
    }

    public void setGruppeKode(String gruppeKode) {
        this.gruppeKode.setValue(gruppeKode);
    }

     public SimpleStringProperty gruppeKodeProperty() {
        return this.gruppeKode;
    }

   public ObservableList<Reisende> getReisende() {
        return reisende;
    }

    public void setReisende(ObservableList<Reisende> reisende) {
        this.reisende = reisende;
    }

    public void addReisende(Reisende r) {
        this.reisende.add(r);
    }
    
    public Integer getAntallReisende() {
        return this.getReisende().size();
    }

    public SimpleIntegerProperty antallReisendeProperty() {
        return new SimpleIntegerProperty(this.getReisende().size());
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
    
     // Custom serialization. Cannot serialize JavaFX properties. We just serialize the contents.
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(this.gruppeKode.get());
        oos.writeObject(new ArrayList<>(this.reisende));
    }

    // Custom unserialization. Put values into their respective property wrappers.
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        this.gruppeKode     = UnserializePropertyFactory.magic((String) ois.readObject());
        this.reisende       = UnserializePropertyFactory.list((List<Reisende>) ois.readObject());
    }
   
    @Override
    public String toString() {
        return this.getGruppeKode();
    }
}

