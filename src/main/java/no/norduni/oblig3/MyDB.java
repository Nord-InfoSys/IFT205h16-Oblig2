/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author mortenj
 */
public class MyDB {

    static MyDB mydb = null;

    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String url = "jdbc:derby:mydbfile;create=true;user=me;password=mine";
    
    Connection conn = null;
    
    public MyDB() {
        try {
            Class.forName(this.driver).newInstance();
            MyDB.dbg("Creating new connection to: " + url);
            this.conn = DriverManager.getConnection(url);


        } catch (Exception ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean execute(String sql) throws SQLException {
        MyDB.dbg("execute: SQL: " + sql);
        Statement s = this.conn.createStatement();
        return s.execute(sql);
    }
    
    public ResultSet executeQuery(String sql) {
        try {
            MyDB.dbg("executeQuery: SQL: " + sql);
            Statement s = this.conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            // s.close();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet executeInsert(String sql) {
        try {
            MyDB.dbg("executeInsert: SQL: " + sql);
            Statement s = this.conn.createStatement();
            Integer r = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return s.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Integer executeUpdate(String sql) {
        try {
            MyDB.dbg("executeUpdate: SQL: " + sql);
            Statement s = this.conn.createStatement();
            Integer r = s.executeUpdate(sql);
            s.close();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public DatabaseMetaData getMetadata() throws SQLException {
        return this.conn.getMetaData();
    }    
    
    
    public boolean tableExists(String tableName) throws SQLException {
        ResultSet rs = this.getMetadata().getTables(null, this.conn.getSchema(), tableName, null);
        if (rs.next()) {
            System.out.println("Table " +  rs.getString(3) + " exists");
            return true;
        }
        return false;
    }
     
    public static MyDB getInstance() {
        if(MyDB.mydb == null) {
            MyDB.mydb = new MyDB();
        }
        return mydb;
    }
    
    public void bootStrapDB() throws SQLException {
        
        try {
            this.execute(
                "CREATE TABLE Reisende "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "Navn VARCHAR(255), "
                        + "Alder INT, "
                        + "Passnr VARCHAR(12), "
                        + "Kjonn VARCHAR(8), "
                        + "Betaling VARCHAR(8))"
            );
            System.out.println("BootStrapped: Reisende!");
        } catch( SQLException e ) {
            System.out.println("NOTE: Reisende finnes fra før..");
        }  
        
       try {
            this.execute(
                "CREATE TABLE Flights "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "FlightNr VARCHAR(255), "
                        + "Origin VARCHAR(255), "
                        + "Destination VARCHAR(12), "
                        + "DepartureTime TIMESTAMP, "
                        + "ArrivalTime TIMESTAMP, "
                        + "Seats INT)"
            );
            System.out.println("BootStrapped: Flights!");
        } catch( SQLException e ) {
            System.out.println("NOTE: Flights finnes fra før..");
        }

        try {
            this.execute(
                "CREATE TABLE Grupper "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "GruppeKode VARCHAR(255))"
            );
            System.out.println("BootStrapped: Grupper!");
        } catch( SQLException e ) {
            System.out.println("NOTE: Grupper finnes fra før..");
        }  

        try {
            this.execute(
                "CREATE TABLE ReisendeOnFlight "
                        + "(FlightID INT, "
                        + "ReisendeID INT)"
            );
            System.out.println("BootStrapped: PassengerOnFlight!");
        } catch( SQLException e ) {
            System.out.println("NOTE: PassengerOnFlight finnes fra før..");
        }  

        try {
            this.execute(
                "CREATE TABLE ReisendeInGruppe "
                        + "(ReisendeID INT, "
                        + "GruppeID INT)"
            );
            System.out.println("BootStrapped: PassengerInGroup!");
        } catch( SQLException e ) {
            System.out.println("NOTE: PassengerInGroup finnes fra før..");
        }  

        try {
            this.execute(
                "CREATE TABLE GruppeOnFlight "
                        + "(FlightID INT, "
                        + "GruppeID INT)"
            );
            System.out.println("BootStrapped: GroupOnFlight!");
        } catch( SQLException e ) {
            System.out.println("NOTE: GroupOnFlight finnes fra før..");
        }  
 
        /*
        if(!this.tableExists("PassengerOnFlight")) {
            //
        }
        if(!this.tableExists("PassengerInGroup")) {
            //
        }
        if(!this.tableExists("GroupOnFlight")) {
            //
        }
    */
   }
             
    public static void dbg(String info) {
        System.out.println(info);
    }
}


