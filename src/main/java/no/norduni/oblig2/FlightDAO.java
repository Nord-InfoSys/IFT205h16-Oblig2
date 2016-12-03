
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author g
 */
public class FlightDAO {

    static Map<Integer, Flight> flights = new TreeMap<>();

    static Flight getInstanceForId(int id) {
        if(!FlightDAO.flights.containsKey(id)) {
            System.out.println("Ny flight-instans!");
            // TODO: Prøv å hent fra SQL
            // Select id fra database, og putt verdiene i det nye objektet (HUSK setDbid())
            Flight f;
            if(FlightDAO.exists(id)) {
                 f = FlightDAO.get(id);
             }
             else {
                 f = new Flight();       
             }
             FlightDAO.flights.put(id, f);
        }
        return FlightDAO.flights.get(id);
    }

    
   /**
     * Sjekker om en flight allerede eksisterer i databasen.
     * @param f
     * @return 
     */
    static Boolean exists(int id) {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT ID FROM Flights WHERE id = %d",id));
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Henter et flight-objekt fra databasen
     * @param id
     * @return 
     */
    private static Flight get(int id) {
        try {
            MyDB db = MyDB.getInstance();
            ResultSet rs = db.executeQuery(String.format("SELECT ID FROM Flight WHERE id = %d",id));
            rs.next();
            
            Flight f = new Flight();
            f.setDbid(id);
            f.setFlightNummer(rs.getString("FlightNr"));
            f.setOrigin(rs.getString("Origin"));
            f.setDestination(rs.getString("Destination"));
            f.setDepartureTime(rs.getTimestamp("DepartureTime").toLocalDateTime());
            f.setArrivalTime(rs.getTimestamp("ArrivalTime").toLocalDateTime());
            f.setAntallPlasser(rs.getInt("Seats"));
 
            f.calcDuration(f.getDepartureTime(),f.getArrivalTime());
            
            
            /*
            f.setGrupper(grupper);
            f.setReisende();
            */

            return f;
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }

    
    
    static void save(Flight f) {
        MyDB db = MyDB.getInstance();
        
        ZoneId zone = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long departureTime = f.getDepartureTime().atZone(zone).toEpochSecond();
        long arrivalTime   = f.getArrivalTime().atZone(zone).toEpochSecond();
                
        if(f.getDbid() == null) {
            // INSERT i databasen, hent tilbake ID, og stapp objektet i Map
            ResultSet rs = db.executeInsert(String.format(
                    "INSERT INTO Flights (FlightNr, Origin, Destination, DepartureTime, ArrivalTime, Seats) VALUES ('%s', '%s', '%s', TIMESTAMP('%s'), TIMESTAMP('%s'), %d)",
                    f.getFlightNummer(),
                    f.getOrigin(),
                    f.getDestination(),
                    String.format("%s %s", f.getDepartureTime().toLocalDate().toString(), f.getDepartureTime().toLocalTime().toString()),
                    String.format("%s %s", f.getArrivalTime().toLocalDate().toString(), f.getDepartureTime().toLocalTime().toString()),
                    f.getAntallPlasser()
            ));
            
            try {
                if(rs.next()) {
                    f.setDbid(rs.getInt(1));
                    FlightDAO.flights.put(f.getDbid(), f);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            db.executeUpdate(String.format(
                "UPDATE Flights SET "
                + "FlightNr = '%s',"
                + "Origin = '%s',"
                + "Destination = '%s',"
                + "DepartureTime = %d,"
                + "ArrivalTime = %d "
                + "WHERE ID = %d",
                f.getFlightNummer(),
                f.getOrigin(),
                f.getDestination(),
                departureTime,
                arrivalTime,
                f.getAntallPlasser()
            ));
        }
        
        // Her er objektet "garantert" lagret i databasen, og det har en database ID.
        // Uansett om objektet var nytt og ikke hadde dbid eller ikke.
        
        // Lagre passasjerer
        FlightDAO.saveReisende(f);
        
    }

    private static void saveReisende(Flight f) {
        MyDB db = MyDB.getInstance();
        
        try {
            // Tøm koblingstabell for alt som har med denne flighten
            db.execute(String.format("DELETE FROM ReisendeOnFlight WHERE FlightID = %d", f.getDbid()));

            // Lagre alle Reisende og oppdater koblinger
            for(Reisende r: f.getReisende()) {
                ReisendeDAO.save(r);
                db.executeInsert(
                    String.format("INSERT INTO ReisendeOnFlight (FlightID, ReisendeID) VALUES (%d, %d)",
                        f.getDbid(),
                        r.getDbid()
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void saveAll(List<Flight> fl) {
        for(Flight f: fl) {
            FlightDAO.save(f);
        }
    }
    
    static void delete(Flight f) {
        MyDB db = MyDB.getInstance();
        try {
            db.execute(String.format(
                    "DELETE FROM Flights WHERE ID = %d",
                    f.getDbid()
            ));
        } catch (SQLException ex) {
            Logger.getLogger(FlightDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
