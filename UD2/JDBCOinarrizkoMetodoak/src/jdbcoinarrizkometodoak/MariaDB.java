/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcoinarrizkometodoak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author salgado.markel
 */
public class MariaDB {
    
    public static Connection connect(String db){
        String server = "localhost";
        String url = "jdbc:mysql://" + server + "/" + db;
        String user = "root";
        String pass = "root";
        
        Connection conn = null;
        
        try {
            conn = (Connection) DriverManager.getConnection(url, user, pass);
            System.out.println("Konektatu zara!");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " - " + e.getMessage());
        }
        return conn;
    }
    
    public static void main(String[] args) {
        //String db="datubaseak/ikasleak.db";
        connect("test");
        
        
    }
}
