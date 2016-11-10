/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author bubbaJ
 */
public class ReisendeController implements Initializable {

    private Reisende reisende;

    @FXML
    private GridPane reisendeView;

    @FXML
    private TextField reisendeNavnField;

    @FXML
    private Slider reisendeAlderField;

    @FXML
    private ChoiceBox<?> gruppeVelger;

    @FXML
    private RadioButton reisendeKjonnRadioMANN;

    @FXML
    private RadioButton reisendeKjonnRadioKVINNE;

    @FXML
    private TextField reisendePassField;

    @FXML
    private CheckBox reisendeBetalingCheckCash;

    @FXML
    private CheckBox reisendeBetalingCheckKort;
    @FXML
    private Button closeButton;

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

    public void setReisende(Reisende reisende) {
        this.reisende = reisende;
 
/*
    *    this.reisendeNavnField
    *    this.reisendeAlderField;
    /    this.gruppeVelger;
        this.reisendeKjonnRadioMANN;
        this.reisendeKjonnRadioKVINNE;
    *    this.reisendePassField;
        this.reisendeBetalingCheckCash;
        this.reisendeBetalingCheckKort;
*/

        this.reisendeNavnField.textProperty().bindBidirectional(this.reisende.navnProperty());
        this.reisendeAlderField.valueProperty().bindBidirectional(this.reisende.alderProperty());

        this.reisendePassField.textProperty().bindBidirectional(this.reisende.passnrProperty());

        
        

    // Bind Grupper List to this.gruppeVelger
    // Hvor i "#%/¤( skal vi hente denne fra?.. det må bli fra parentFlight ??
//    this.gruppeVelger.setItems(this.flight.getGrupper());
        
    }
}
