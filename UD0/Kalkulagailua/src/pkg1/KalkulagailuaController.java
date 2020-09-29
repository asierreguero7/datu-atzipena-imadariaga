/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.lang.Math;

/**
 *
 * @author root
 */
public class KalkulagailuaController implements Initializable {
    
    @FXML
    private Label emaitza;
    
    @FXML
    private Label op;
    
    @FXML
    private TextField z1, z2;
    
    @FXML
    private void handleGehiAction(ActionEvent event) {
        System.out.println("Batuketa egin dugu.");
        emaitza.setText(""+(Double.parseDouble(z1.getText())+Double.parseDouble(z2.getText())));
    }

    @FXML
    private void handleKenAction(ActionEvent event) {
        System.out.println("Kenketa egin dugu.");
        emaitza.setText(""+(Double.parseDouble(z1.getText()) - Double.parseDouble(z2.getText())));
    }
    
    @FXML
    private void handleBiderAction(ActionEvent event) {
        System.out.println("Biderketa egin dugu.");
        emaitza.setText(""+(Double.parseDouble(z1.getText()) * Double.parseDouble(z2.getText())));
    }
    
    @FXML
    private void handleZatiAction(ActionEvent event) {
        System.out.println("Zatiketa egin dugu.");
        emaitza.setText(""+(Double.parseDouble(z1.getText()) / Double.parseDouble(z2.getText())));
    }
    
    @FXML
    private void handleBerAction(ActionEvent event) {
        System.out.println("Berreketa egin dugu.");
        double ber=Double.parseDouble(z1.getText());
        for(int i=0;i<Double.parseDouble(z2.getText())-1;i++){
            ber = ber * Double.parseDouble(z1.getText());
        }
        emaitza.setText(""+ber);
    }
    @FXML
    private void handleErroAction(ActionEvent event) {
        System.out.println("Berreketa egin dugu.");
        emaitza.setText(""+Math.pow(Double.parseDouble(z1.getText()), 1/Double.parseDouble(z2.getText())));
    }
    @FXML
    private void hoverGehi(ActionEvent event) {
        op.setText("+");
    }
    @FXML
    private void hoverKen(ActionEvent event) {
        op.setText("-");
    }
    @FXML
    private void hoverBider(ActionEvent event) {
        op.setText("x");
    }
    @FXML
    private void hoverZati(ActionEvent event) {
        op.setText("/");
    }
    @FXML
    private void hoverBer(ActionEvent event) {
        op.setText("^");
    }
    @FXML
    private void hoverErro(ActionEvent event) {
        op.setText("√");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
