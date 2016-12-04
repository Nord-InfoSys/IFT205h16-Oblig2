/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author bubbaJ
 */
public class GruppeController implements Initializable {

    private Gruppe gruppe;

    @FXML
    private Button closeButton;
    @FXML
    private TextField gruppeNavnField;
    @FXML
    private GridPane gruppeView;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
        this.gruppeNavnField.textProperty().bindBidirectional(this.gruppe.gruppeKodeProperty());
        
    }
}
