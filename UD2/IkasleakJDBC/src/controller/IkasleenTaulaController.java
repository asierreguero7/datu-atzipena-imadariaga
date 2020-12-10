/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import model.Ikaslea;
import model.MariaDb;

/**
 * FXML Controller class
 *
 * @author IMadariaga
 */
public class IkasleenTaulaController implements Initializable {

    @FXML
    private Label izenburua;
    @FXML
    private TableView<Ikaslea> tableviua;
    @FXML
    private TableColumn<Ikaslea, Integer> zenbakiaCol;
    @FXML
    private TableColumn<Ikaslea, String> izenaCol;
    @FXML
    private TableColumn<Ikaslea, String> abizena1Col;
    @FXML
    private HBox botoiak;//
    @FXML
    private TextField addZenbakia;
    @FXML
    private TextField addIzena;
    @FXML
    private TextField addAbizena;
    @FXML
    private Button gehitu;
    @FXML
    private Button ezabatu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("IkasleenTaula eszena inizializatzen dabil");
            ObservableList<Ikaslea> ikZerrendaObservablea = MariaDb.datuakMemorianKargatu();
            
            //TableView-ko lerroak  ObservableList-arekin lotuko ditugu
            tableviua.setItems(ikZerrendaObservablea);
            
            //Lerro bakoitzean "List"-ako objetu bat bistaratuko da.
            //Zutabe bakoitza zein atributorekin dagoen lotuta definituko dugu (zutabeko zeldak zein atributorekin beteko diren)
            zenbakiaCol.setCellValueFactory(new PropertyValueFactory<Ikaslea, Integer>("zenbakia"));//ObservableListaren elementu bakoitzeko zein atributo dagokion zutabe honi
            izenaCol.setCellValueFactory(new PropertyValueFactory<Ikaslea, String>("izena"));
            abizena1Col.setCellValueFactory(new PropertyValueFactory<Ikaslea, String>("abizena1"));
            
            //Table view elementua fxml-an editable dela adierazita daukagula, zelden editatzeak ondo funtzionatzeko:
            zenbakiaCol.setCellFactory(TextFieldTableCell.<Ikaslea, Integer>forTableColumn(new IntegerStringConverter()));
            zenbakiaCol.setOnEditCommit((TableColumn.CellEditEvent<Ikaslea, Integer> t) -> {
                ((Ikaslea) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setZenbakia(t.getNewValue());
            });
            
            izenaCol.setCellFactory(TextFieldTableCell.<Ikaslea>forTableColumn());
            izenaCol.setOnEditCommit((TableColumn.CellEditEvent<Ikaslea, String> t) -> {
                ((Ikaslea) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setIzena(t.getNewValue());
            });
            
            abizena1Col.setCellFactory(TextFieldTableCell.<Ikaslea>forTableColumn());
            abizena1Col.setOnEditCommit((TableColumn.CellEditEvent<Ikaslea, String> t) -> {
                ((Ikaslea) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setAbizena1(t.getNewValue());
            });
        } catch (SQLException ex) {
            Logger.getLogger(IkasleenTaulaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @FXML
    private void handleGehituAction(ActionEvent event) {
        if (tableviua.getItems().size() < 7) {
            if (isNumeric(addZenbakia.getText())) {
                System.out.println("Gehitu botoia sakatu duzu!");
                //izenburua.setText("Gehitzen...");
                Ikaslea p = new Ikaslea(
                        Integer.parseInt(addZenbakia.getText()),
                        addIzena.getText(),
                        addAbizena.getText());
                tableviua.getItems().add(p);
                System.out.println("Ikasle berria gehitu da.");
                addZenbakia.setText("");
                addIzena.setText("");
                addAbizena.setText("");
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("ADI!!!");
                alert.setHeaderText("KONTUZ!!!");
                alert.setContentText("Zenbakia zenbaki bat izan behar da");

                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ADI!!!");
            alert.setHeaderText("KONTUZ!!!");
            alert.setContentText("Gehienez 7 ikasle egon ahal dira");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEzabatuAction(ActionEvent event) {
        System.out.println("Ezabatu botoia sakatu duzu!");
        Ikaslea ikaslea = tableviua.getSelectionModel().getSelectedItem();
        tableviua.getItems().remove(ikaslea);
        System.out.println("Aukeratutako ikaslea ezabatua izan da.");
    }

}
