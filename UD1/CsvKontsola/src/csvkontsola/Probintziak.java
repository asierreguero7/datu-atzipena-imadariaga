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
public class Probintziak {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        String line;
        ArrayList<String> prob = new ArrayList<>();
        PrintWriter outputStream = null;

        try {
            br = new BufferedReader(new FileReader("mendiak.csv"));
            int lerroak = 1;

            while ((line = br.readLine()) != null) {
                if (lerroak != 1) {
                    String[] mendiak = line.split(";");
                    if (!prob.contains(mendiak[2])) {
                        prob.add(mendiak[2]);
                    }
                }
                lerroak++;
            }
            for (int i = 0; i < prob.size(); i++) {
                br = new BufferedReader(new FileReader("mendiak.csv"));
                outputStream = new PrintWriter(new FileWriter(prob.get(i).toLowerCase()+".csv"));
                outputStream.println("MENDIA;ALTUERA;PROBINTZIA");
                
                while ((line = br.readLine()) != null) {
                        String[] mendiak = line.split(";");
                        if (mendiak[2].equals(prob.get(i))) {
                            outputStream.println(line);
                        }
                }
                outputStream.close();
            }
            System.out.println("Probintzien artxiboak sortu dira");
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
