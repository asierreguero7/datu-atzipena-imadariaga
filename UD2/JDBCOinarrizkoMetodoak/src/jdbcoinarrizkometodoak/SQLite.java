/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcoinarrizkometodoak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;


/**
 *
 * @author sqlitetutorial.net
 */
public class SQLite {
     /**
     * SQLite to a sample database
     */
    int id=1;
    public static void connect(String db) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:datubaseak/";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:datubaseak/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:datubaseak/test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS irakasleak (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	izena text NOT NULL\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:datubaseak/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param capacity
     */
    public void insert(String izena) {
        String sql = "INSERT INTO irakasleak (id, izena) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, izena);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if(e.getErrorCode() == 19){
                System.out.println("Txertatu nahi duzun irakaslearen Id-a dagoeneko existitzen da!!!");  
            }
            
        }
        id++;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String db="datubaseak/markelGuapo.db";
        //connect(db);
        
        createNewDatabase("test.db");
        
        createNewTable();

        SQLite app = new SQLite();
        
        // insert three new rows
        app.insert("madariaga");
        app.insert("franco");
    }   
}