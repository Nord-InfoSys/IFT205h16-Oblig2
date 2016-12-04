/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

import javafx.collections.ObservableList;

/**
 *
 * @author mortenj
 */
public class ReisendeSearcher {
    private ObservableList<Flight> flighter = javafx.collections.FXCollections.observableArrayList();
    private final ObservableList<Reisende> resultat = javafx.collections.FXCollections.observableArrayList();

    public ObservableList<Flight> getFlighter() {
        return flighter;
    }

    public void setFlighter(ObservableList<Flight> flighter) {
        this.flighter = flighter;
    }
    
    public ObservableList<Reisende> getResultat() {
        return resultat;
    }
    
    public void searchPassnr(String search) {
        resultat.clear();
        for(Flight f: flighter) {
            for(Reisende r: f.getReisendeByPassnrSearch(search)) {
                if(!resultat.contains(r))
                    resultat.add(r);
            }
        }
        for(Reisende r: resultat) {
            System.out.println(r.getNavn()+" "+r.getPassnr());
        }
    }
}
