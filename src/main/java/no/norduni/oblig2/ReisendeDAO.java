
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
    
        static void getAllInstancesOnFlight(Flight f) throws Exception {
            
            try {
                MyDB db = MyDB.getInstance();
                ResultSet rs = db.executeQuery(String.format("SELECT ReisendeID FROM ReisendeOnFlight WHERE FlightID = %d",f.getDbid()));
                while (rs.next()) {
                    f.addReisende(ReisendeDAO.getInstanceForId(rs.getInt("ReisendeID")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        static void getAllInstancesOnGruppe(Gruppe g) throws Exception {
            
            try {
                MyDB db = MyDB.getInstance();
                ResultSet rs = db.executeQuery(String.format("SELECT ReisendeID FROM ReisendeInGruppe WHERE GruppeID = %d",g.getDbid()));
                while (rs.next()) {
                    Reisende r = ReisendeDAO.getInstanceForId(rs.getInt("ReisendeID"));
                    r.setGruppe(g);
                    g.addReisende(r);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

        static Reisende getInstanceForId(int id) {
        // Dersom vi allerede har instansiert et objekt for ID,
        // så finnes det i reisende-map'en.
        if(!ReisendeDAO.reisende.containsKey(id)) {
            System.out.println("Ny instans!");
            Reisende r;
            if(ReisendeDAO.exists(id)) {
                r = ReisendeDAO.get(id);
            }
            else {
                r = new Reisende();        
            }
            ReisendeDAO.reisende.put(id, r);
        }
        return ReisendeDAO.reisende.get(id);
    }
    
    /**
     * Sjekker om en reisende allerede eksisterer i databasen.
     * @param r
     * @return 
     */
    static Boolean exists(int id) {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT ID FROM Reisende WHERE id = %d",id));
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Henter et reisende-objekt fra databasen
     * @param id
     * @return 
     */
    private static Reisende get(int id) {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT * FROM Reisende WHERE id = %d",id));
            rs.next();
            Reisende r = new Reisende();
            r.setAlder(rs.getInt("Alder"));
            // TODO betaling, gruppe og kjønn
//            r.setBetaling();
            r.setDbid(id);
//            r.setGruppe(rs.getString("Gruppe"));
//            r.setKjonn();
            r.setNavn(rs.getString("Navn"));
            r.setPassnr(rs.getString("Passnr"));
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
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
            db.executeUpdate(String.format(
                "UPDATE Reisende SET "
                + "Navn = '%s',"
                + "Alder = %d,"
                + "Passnr = '%s',"
                + "Kjonn = '%s',"
                + "Betaling = '%s' "
                + "WHERE ID = %d",
                r.getNavn(),
                r.getAlder(),
                r.getPassnr(),
                r.getKjonn(),
                r.getBetaling().getMetode(),
                r.getDbid()
            ));
        }
    }

    static void delete(Reisende r) {
        MyDB db = MyDB.getInstance();
        try {
            db.execute(String.format(
                    "DELETE FROM Reisende WHERE ID = %d",
                    r.getDbid()
            ));
        } catch (SQLException ex) {
            Logger.getLogger(ReisendeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
