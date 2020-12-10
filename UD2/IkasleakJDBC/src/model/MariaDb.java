/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static global.Global.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author reguero.asier
 */
public class MariaDb {
    public static Connection konektatu(){
        String url = "jdbc:mysql://" + ZERBITZARIA + "/" + DATUBASEA;
        String user = erabiltzailea;
        String pass = pasahitza;
        
        Connection conn = null;
        
        try {
            conn = (Connection) DriverManager.getConnection(url, user, pass);
            System.out.println("Konektatu zara!");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " - " + e.getMessage());
        }
        return conn;
    }
    
    public static ObservableList datuakMemorianKargatu() throws SQLException{
        ObservableList<Ikaslea> data = FXCollections.observableArrayList();
        Statement a = null;
        ResultSet rs =null;
        Connection konekzioa = konektatu();
        try{
            a = konekzioa.createStatement();
            rs = a.executeQuery("SELECT * FROM ikasleak");
            while(rs.next()){
                int zenbakia = rs.getInt("zenbakia");
                String izena = rs.getString("izena");
                String abizena = rs.getString("abizena");
                Ikaslea ikasle = new Ikaslea(zenbakia, izena, abizena);
                data.add(ikasle);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public static void main(String[] args) {
        //String db="datubaseak/ikasleak.db";
        Connection conn;
        conn = konektatu();
        
        
    }
}
