/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author reguero.asier
 */
public class Handiena {
    public static void main(String[] args) throws IOException {
        int altuera = 0;
        String izena = "";
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader("mendiak.csv"));
            int lerroak = 1;

            while ((line = br.readLine()) != null) {
                if (lerroak != 1) {
                    String[] mendiak = line.split(";");
                    if (Integer.parseInt(mendiak[1]) > altuera) {
                        altuera = Integer.parseInt(mendiak[1]);
                        izena = mendiak[0];
                    }
                }
                lerroak++;
            }
            System.out.println("Mendi altuena " + izena + " da. " + altuera + " metro ditu");
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
