/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

/**
 *
 * @author mortenj
 */
public class Betaling {
    private Betalingsmetode metode;
    // XXX: Her kunne det vært attributter som innbetalt beløp osv,
    //      men oppgaven krever det ikke. Klassen er slik i fall
    //      det blir behov senere.

    public Betaling(Betalingsmetode metode) {
        this.metode = metode;
    }

    public Betalingsmetode getMetode() {
        return metode;
    }

    public void setMetode(Betalingsmetode metode) {
        this.metode = metode;
    }
    
    @Override
    public String toString() {
        return this.getMetode().toString();
    }
    
}

