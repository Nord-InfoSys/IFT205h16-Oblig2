/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

/**
 *
 * @author bubbaJ
 */
public class Betaling extends ModelBase {
    private Betalingsmetode metode;

    
    public Betaling(Betalingsmetode metode) {
        this.metode = metode;
    }

    public Betalingsmetode getMetode() {
        return metode;
    }

    public void setMetode(Betalingsmetode metode) {
        this.metode = metode;
    }
    
    public Betalingsmetode metodeProperty() {
        return metode;
    }
    
    @Override
    public String toString() {
        return this.getMetode().toString();
    }
    
}

