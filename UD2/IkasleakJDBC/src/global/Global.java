/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author reguero.asier
 */
public class Global {
    public static final String ZERBITZARIA = "localhost";
    public static final String  DATUBASEA = "ikasleak";
    
    public static String erabiltzailea = "root";
    public static String pasahitza = "root";

    public static String getErabiltzailea() {
        return erabiltzailea;
    }

    public static void setErabiltzailea(String erablitzailea) {
        Global.erabiltzailea = erablitzailea;
    }

    public static String getPasahitza() {
        return pasahitza;
    }

    public static void setPasahitza(String pasahitza) {
        Global.pasahitza = pasahitza;
    }
    
    
}
