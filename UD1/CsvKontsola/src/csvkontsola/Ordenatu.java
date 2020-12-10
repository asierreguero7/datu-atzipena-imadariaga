/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author reguero.asier
 */
public class Ordenatu {

    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        String line;
        ArrayList<Mendia> mendiakAltueraz = new ArrayList<>();
        PrintWriter outputStream = null;
        try {
            br = new BufferedReader(new FileReader("mendiak.csv"));
            outputStream = new PrintWriter("mendiakOrdenatuta.csv");
            
            int lerroak = 1;
            boolean sartuta=false;
            
            //altueraz
            while ((line = br.readLine()) != null) {
                if (lerroak != 1) {
                    sartuta=false;
                    String[] mendiak = line.split(";");
                    if(lerroak==2){
                        mendiakAltueraz.add(new Mendia(mendiak[0], Integer.parseInt(mendiak[1]), mendiak[2]));
                    }else{
                        for(int i = 0;i<mendiakAltueraz.size();i++){
                            if(Integer.parseInt(mendiak[1])>mendiakAltueraz.get(i).getAltuera()){
                                Mendia mendia = new Mendia(mendiak[0], Integer.parseInt(mendiak[1]), mendiak[2]);
                                mendiakAltueraz.add(i, mendia);
                                sartuta=true;
                                break;
                            }
                        }
                    }
                    if(!sartuta){
                        Mendia mendia = new Mendia(mendiak[0], Integer.parseInt(mendiak[1]), mendiak[2]);
                        mendiakAltueraz.add(mendia);
                    }
                }
                lerroak++;
            }
            
            outputStream.println("MENDIA;ALTUERA;PROBINTZIA");
            for(int i = 0;i<mendiakAltueraz.size();i++){
                Mendia mendia = mendiakAltueraz.get(i);
                outputStream.println(mendia.getIzena() + ";" + mendia.getAltuera() + ";" + mendia.getProbintzia());
            }
            
            
            outputStream.close();
            System.out.println("Altueraz ordenatu dira mendiak");
        } catch (FileNotFoundException e) {
            System.out.println("Artxiboa ez dago bere tokian");
        } catch (IOException e) {
            System.out.println("Sarrera/irteera errore bat gertatu da");
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
