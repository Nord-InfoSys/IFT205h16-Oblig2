/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
    private ChoiceBox<String> gruppeVelger;

    @FXML
    private RadioButton reisendeKjonnRadioMANN;

    @FXML
    private RadioButton reisendeKjonnRadioKVINNE;

    @FXML
    private TextField reisendePassField;

    @FXML
    private RadioButton reisendeBetalingCheckCash;

    @FXML
    private RadioButton reisendeBetalingCheckKort;
    @FXML
    private Button closeButton;
    @FXML
    private ToggleGroup KjonnToggle;
    @FXML
    private ToggleGroup BetalingToggle;

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    
    ObservableList<String> GruppeChoiceList = FXCollections.observableArrayList("A","B");
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gruppeVelger.setItems(GruppeChoiceList);
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

   // Kjør på med en observable list her så er det go!     
   //     gruppeVelger.setItems(this.flight.getGruppeProperties);
        
        
        KjonnToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (KjonnToggle.getSelectedToggle() != null) {
 
                    RadioButton KjonnKnapp = (RadioButton)KjonnToggle.getSelectedToggle(); // Cast object to radio button
                     if ("KVINNE".equals(KjonnKnapp.getText())) {
                        ReisendeController.this.reisende.setKjonn(Kjonn.KVINNE);
                    } else {
                        ReisendeController.this.reisende.setKjonn(Kjonn.MANN);
                    }
                    
                }
            }
        });      

        BetalingToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (BetalingToggle.getSelectedToggle() != null) {
 
                    RadioButton BetalingKnapp = (RadioButton)BetalingToggle.getSelectedToggle(); // Cast object to radio button
                     if ("CASH".equals(BetalingKnapp.getText())) {
                        ReisendeController.this.reisende.setBetaling(new Betaling(Betalingsmetode.CASH));
                    } else {
                        ReisendeController.this.reisende.setBetaling(new Betaling(Betalingsmetode.KREDITT));
                   }
                    
                }
            }
        });      

        
        

    // Bind Grupper List to this.gruppeVelger
    // Hvor i "#%/¤( skal vi hente denne fra?.. det må bli fra parentFlight ??
//    this.gruppeVelger.setItems(this.flight.getGrupper());
        
    }
}
