
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
 * @author g
 */
public class FlightDAO {

    static Map<Integer, Flight> flights = new TreeMap<>();

    static Flight getInstanceForId(int id) {
        if(!FlightDAO.flights.containsKey(id)) {
            System.out.println("Ny flight-instans!");
            // TODO: Prøv å hent fra SQL
            // Select id fra database, og putt verdiene i det nye objektet (HUSK setDbid())
            Flight f = new Flight();
            FlightDAO.flights.put(id, f);
        }
        return FlightDAO.flights.get(id);
    }
    
}
