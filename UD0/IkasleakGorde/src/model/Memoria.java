/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author root
 */
public class Memoria {

    public static ObservableList<Ikaslea> zerrendaSortu() throws FileNotFoundException, IOException {
        ArrayList<Ikaslea> ikasleak = new ArrayList();
        BufferedReader br = null;
        PrintWriter outputStream = null;
        String line;
        try {
            br = new BufferedReader(new FileReader("ikasleak.csv"));

            while ((line = br.readLine()) != null) {
                String[] ikasle = line.split(",");
                ikasleak.add(new Ikaslea(Integer.parseInt(ikasle[0]), ikasle[1], ikasle[2]));
            }
        } catch(FileNotFoundException ex)  {
            ex.printStackTrace();
        }
        ObservableList<Ikaslea> oIkasleak = FXCollections.observableArrayList(ikasleak);
        return oIkasleak;
    }
}
