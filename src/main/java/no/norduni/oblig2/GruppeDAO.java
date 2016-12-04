
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author g
 */
public class GruppeDAO {

    static Map<Integer, Gruppe> grupper = new TreeMap<>();

    static List<Gruppe> getAllInstancesForFlight(Flight f) throws Exception {
        List<Gruppe> liste = new ArrayList();
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT GruppeID FROM GruppeOnFlight WHERE FlightID = %d",f.getDbid()));
            while (rs.next()) {
                liste.add(GruppeDAO.getInstanceForId(rs.getInt("GruppeID")));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(GruppeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    static Gruppe getInstanceForId(int id) throws Exception {
        if(!GruppeDAO.grupper.containsKey(id)) {
            System.out.println("Ny gruppe-instans!");
            // TODO: Prøv å hent fra SQL
            // Select id fra database, og putt verdiene i det nye objektet (HUSK setDbid())
            Gruppe g;
            if(GruppeDAO.exists(id)) {
                 g = GruppeDAO.get(id);
             }
             else {
                 return null;      
             }
             GruppeDAO.grupper.put(id, g);
        }
        return GruppeDAO.grupper.get(id);
    }

    
   /**
     * Sjekker om en gruppe allerede eksisterer i databasen.
     * @param f
     * @return 
     */
    static Boolean exists(int id) {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT ID FROM Grupper WHERE id = $d",id));
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Henter et gruppe-objekt fra databasen
     * @param id
     * @return 
     */
    private static Gruppe get(int id) throws Exception {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT * FROM Grupper WHERE id = %d",id));
            rs.next();
            
            Gruppe g = new Gruppe();
            g.setDbid(id);
            g.setGruppeKode(rs.getString("GruppeKode"));
            ReisendeDAO.getAllInstancesOnGruppe(g);
            return g;
        } catch (SQLException ex) {
            Logger.getLogger(GruppeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }

    static void save(Gruppe g) {
        MyDB db = MyDB.getInstance();
        
        if(g.getDbid() == null) {
            // INSERT i databasen, hent tilbake ID, og stapp objektet i Map
            ResultSet rs = db.executeInsert(String.format(
                    "INSERT INTO Grupper (GruppeKode) VALUES ('%s')",
                    g.getGruppeKode()
            ));
            
            try {
                if(rs.next()) {
                    g.setDbid(rs.getInt(1));
                    GruppeDAO.grupper.put(g.getDbid(), g);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            db.executeUpdate(String.format(
                "UPDATE Grupper SET "
                + "GruppeKode = '%s' "
                + "WHERE ID = %d",
                g.getGruppeKode(),
                g.getDbid()
            ));
        }
        
        // Nå er gruppen "garantert" lagret (har dbid). Sørg for at reisende i gruppen lagres til db.
        GruppeDAO.saveReisende(g);
    }

    private static void saveReisende(Gruppe g) {
        MyDB db = MyDB.getInstance();
        
        try {
            // Tøm koblingstabell for alt som har med denne gruppen
            db.execute(String.format("DELETE FROM ReisendeInGruppe WHERE GruppeID = %d", g.getDbid()));

            // Lagre alle Reisende og oppdater koblinger
            for(Reisende r: g.getReisende()) {
                ReisendeDAO.save(r);
                db.executeInsert(
                    String.format("INSERT INTO ReisendeInGruppe (GruppeID, ReisendeID) VALUES (%d, %d)",
                        g.getDbid(),
                        r.getDbid()
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void delete(Gruppe g) {
        MyDB db = MyDB.getInstance();
        try {
            db.execute(String.format(
                    "DELETE FROM Grupper WHERE ID = %d",
                    g.getDbid()
            ));
        } catch (SQLException ex) {
            Logger.getLogger(GruppeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
}
