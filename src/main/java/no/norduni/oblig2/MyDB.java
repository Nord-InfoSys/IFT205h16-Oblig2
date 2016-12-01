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

    public ResultSet execute(String sql) {
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
    
    public static MyDB getInstance() {
        if(MyDB.mydb == null) {
            MyDB.mydb = new MyDB();
        }
        return mydb;
    }
    
    public static void dbg(String info) {
        System.out.println(info);
    }
}


