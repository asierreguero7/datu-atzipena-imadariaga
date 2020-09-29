/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author root
 */
public class Memoria {
    
    public static ObservableList<Ikaslea> zerrendaSortu(){
        return FXCollections.observableArrayList(
                new Ikaslea(1,"Markel","Salgado"),
                new Ikaslea(2, "Pablo", "Lopez"),
                new Ikaslea(3, "Alvaro", "Higuera"),
                new Ikaslea(4, "Asier", "Reguero"),   
                new Ikaslea(5, "Joseba", "Hernandez"),
                new Ikaslea(6, "Jon", "Unzalu"));
    }
}
