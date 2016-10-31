/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mortenj
 */
public class Gruppe {
    private String      gruppeKode;
    private List<Reisende>  reisende;

    public Gruppe(String gruppeKode) {
        this.gruppeKode = gruppeKode;
        this.reisende = new ArrayList();
    }

    public Gruppe(String gruppeKode, List<Reisende> reisende) {
        this.gruppeKode = gruppeKode;
        this.reisende = reisende;
    }

    public String getGruppeKode() {
        return gruppeKode;
    }

    public void setGruppeKode(String gruppeKode) {
        this.gruppeKode = gruppeKode;
    }

    public List<Reisende> getReisende() {
        return reisende;
    }

    public void setReisende(List<Reisende> reisende) {
        this.reisende = reisende;
    }

    public void addReisende(Reisende r) {
        this.reisende.add(r);
    }
    
    public Integer getAntallReisende() {
        return this.getReisende().size();
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
    
}

