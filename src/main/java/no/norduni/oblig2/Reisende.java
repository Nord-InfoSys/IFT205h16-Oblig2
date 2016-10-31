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
public class Reisende {
    private String navn;
    private Kjonn kjonn;
    private int alder;
    private String passnr;
    private Betaling betaling;

    public Reisende(String navn, Kjonn kjonn, int alder, String passnr) {
        this.navn = navn;
        this.kjonn = kjonn;
        this.alder = alder;
        this.passnr = passnr;
        this.betaling = null;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Kjonn getKjonn() {
        return kjonn;
    }

    public void setKjonn(Kjonn kjonn) {
        this.kjonn = kjonn;
    }

    public int getAlder() {
        return alder;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    public String getPassnr() {
        return passnr;
    }

    public void setPassnr(String passnr) {
        this.passnr = passnr;
    }

    public void setBetaling(Betaling betaling) {
        this.betaling = betaling;
    }
    
    public Betaling getBetaling() {
        return betaling;
    }

}
