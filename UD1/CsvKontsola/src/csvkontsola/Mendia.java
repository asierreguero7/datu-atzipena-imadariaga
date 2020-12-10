/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

/**
 *
 * @author reguero.asier
 */
public class Mendia {
    private String izena;
    private int altuera;
    private String probintzia;

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public int getAltuera() {
        return altuera;
    }

    public void setAltuera(int altuera) {
        this.altuera = altuera;
    }

    public String getProbintzia() {
        return probintzia;
    }

    public void setProbintzia(String probintzia) {
        this.probintzia = probintzia;
    }

    public Mendia(String izena, int altuera, String probintzia) {
        this.izena = izena;
        this.altuera = altuera;
        this.probintzia = probintzia;
    }
    
}