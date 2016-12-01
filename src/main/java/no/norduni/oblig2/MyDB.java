/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

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
    
    Connection conn;
    
    public MyDB() {
        try {
            Class.forName(this.driver).newInstance();
            MyDB.dbg("Creating new connection to: " + url);
            this.conn = DriverManager.getConnection(url);
            
            
        } catch (Exception ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean execute(String sql) throws SQLException {
        MyDB.dbg("Executing SQL: " + sql);
        Statement s = this.conn.createStatement();
        return s.execute(sql);
    }
    
    public ResultSet executeQuery(String sql) {
        try {
            MyDB.dbg("Executing SQL: " + sql);
            Statement s = this.conn.createStatement();
            ResultSet r = s.executeQuery(sql);
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
                        + "Kjonn VARCHAR(6), "
                        + "Betaling VARCHAR(6))"
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
                        + "FlightDuration INT, "
                        + "Seats INT)"
            );
            System.out.println("BootStrapped: Flights!");
        } catch( SQLException e ) {
            System.out.println("NOTE: Flights finnes fra før..");
        }

        try {
            this.execute(
                "CREATE TABLE PassengerOnFlight "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "FlightID INT, "
                        + "ReisendeID INT)"
            );
            System.out.println("BootStrapped: PassengerOnFlight!");
        } catch( SQLException e ) {
            System.out.println("NOTE: PassengerOnFlight finnes fra før..");
            return; // That's OK
        }  

        try {
            this.execute(
                "CREATE TABLE PassengerInGroup "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "ReisendeID INT, "
                        + "GruppeID INT)"
            );
            System.out.println("BootStrapped: PassengerInGroup!");
        } catch( SQLException e ) {
            System.out.println("NOTE: PassengerInGroup finnes fra før..");
            return; // That's OK
        }  

        try {
            this.execute(
                "CREATE TABLE GroupOnFlight "
                        + "(ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "FlightID INT, "
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


