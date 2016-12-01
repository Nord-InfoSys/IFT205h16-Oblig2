
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mortenj
 */
public class ReisendeDAO {
    // Map av allerede instansierte objekter fra databasen.
    static Map<Integer, Reisende> reisende = new TreeMap<>();
    
    static Reisende getInstanceForId(int id) {
        // Dersom vi allerede har instansiert et objekt for ID,
        // så finnes det i reisende-map'en.
        if(!ReisendeDAO.reisende.containsKey(id)) {
            System.out.println("Ny instans!");
            // TODO: Prøv å hent fra SQL
            // Select id fra database, og putt verdiene i det nye objektet (HUSK setDbid())
            Reisende r = new Reisende();
            ReisendeDAO.reisende.put(id, r);
        }
        return ReisendeDAO.reisende.get(id);
    }

    static void save(Reisende r) {
        MyDB db = MyDB.getInstance();
        
        if(r.getDbid() == null) {
            // INSERT i databasen, hent tilbake ID, og stapp objektet i Map
            ResultSet rs = db.executeInsert(String.format(
                    "INSERT INTO Reisende (Navn, Alder, Passnr, Kjonn, Betaling) VALUES ('%s', %d, '%s', '%s', '%s')",
                    r.getNavn(),
                    r.getAlder(),
                    r.getPassnr(),
                    r.getKjonn(),
                    r.getBetaling().getMetode()
            ));
            
            try {
                if(rs.next()) {
                    r.setDbid(rs.getInt(1));
                    ReisendeDAO.reisende.put(r.getDbid(), r);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // TODO: UPDATE i databasen ala det insert gjør, men da vet vi jo allerede ID ;-)
        }
    }

    static void delete(Reisende r) {
    }

}
