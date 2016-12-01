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
                        + "(ID INT PRIMARY KEY, "
                        + "Name VARCHAR(255), "
                        + "Age INT, "
                        + "Passport VARCHAR(12), "
                        + "Sex VARCHAR(6), "
                        + "Payment VARCHAR(6), "
                        + "Gruppe VARCHAR(255), "
                        + "ShavenBalls BOOLEAN)"
            );
        } catch( SQLException e ) {
         //   if( DerbyHelper.tableAlreadyExists( e ) ) {
                return; // That's OK
         //   }
         //   throw e;
        }  
        
        if(!this.tableExists("Flights")) {
            //
        }
        if(!this.tableExists("PassengerOnFlight")) {
            //
        }
        if(!this.tableExists("PassengerInGroup")) {
            //
        }
        if(!this.tableExists("GroupOnFlight")) {
            //
        }
    
        /*
        try{
            this.execute("ost");
        } catch( SQLException e ) {
            if( DerbyHelper.tableAlreadyExists( e ) ) {
                return; // That's OK
            }
            throw e;
        }        
        */
    }
 
/*    
    public boolean bootStrapDB() {
        if(!this.isBootStrapped()) {
            this.tableExists(driver)
        }
        return true;
    }
*/
            
    public static void dbg(String info) {
        System.out.println(info);
    }
}


