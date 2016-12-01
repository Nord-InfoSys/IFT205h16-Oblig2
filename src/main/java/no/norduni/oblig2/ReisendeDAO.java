
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author mortenj
 */
public class ReisendeDAO {
    static Map<Integer, Reisende> reisende = new TreeMap<>();
    
    static Reisende getInstanceForId(int id) {
        if(!ReisendeDAO.reisende.containsKey(id)) {
            System.out.println("Ny instans!");
            Reisende r = new Reisende();
            ReisendeDAO.reisende.put(id, r);
        }
        return ReisendeDAO.reisende.get(id);
    }

    static void save(Reisende r) {
    }

    static void delete(Reisende r) {
    }

}
