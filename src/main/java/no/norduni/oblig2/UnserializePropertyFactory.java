/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author mortenj
 */
public class UnserializePropertyFactory {
    
    public static SimpleBooleanProperty magic(Boolean v) {
        SimpleBooleanProperty ret = new SimpleBooleanProperty();
        ret.set(v);
        return ret;
    }
    
    public static SimpleIntegerProperty magic(Integer v) {
        SimpleIntegerProperty ret = new SimpleIntegerProperty();
        ret.set(v);
        return ret;
    }
    
    public static SimpleStringProperty magic(String v) {
        SimpleStringProperty ret = new SimpleStringProperty();
        ret.set(v);
        return ret;
    }

    public static SimpleObjectProperty magic(LocalDateTime v) throws IOException, ClassNotFoundException  {
        SimpleObjectProperty ret = new SimpleObjectProperty();
        ret.set((LocalDateTime) v);
        return ret;
    }

    public static SimpleObjectProperty magic(Duration v) throws IOException, ClassNotFoundException  {
        SimpleObjectProperty ret = new SimpleObjectProperty();
        ret.set((Duration) v);
        return ret;
    }

    public static SimpleObjectProperty magic(Kjonn v) throws IOException, ClassNotFoundException  {
        SimpleObjectProperty ret = new SimpleObjectProperty();
        ret.set((Kjonn) v);
        return ret;
    }
    
    public static SimpleObjectProperty magic(Betaling v) throws IOException, ClassNotFoundException  {
        SimpleObjectProperty ret = new SimpleObjectProperty();
        ret.set((Betaling) v);
        return ret;
    }

    public static SimpleObjectProperty magic(Gruppe v) throws IOException, ClassNotFoundException  {
        SimpleObjectProperty ret = new SimpleObjectProperty();
        ret.set((Gruppe) v);
        return ret;
    }

    public static ObservableList list(List l) {
        ObservableList ret = javafx.collections.FXCollections.observableArrayList();
        ret.setAll(l);
        return ret;
    }
}
