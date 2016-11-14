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
    private ObservableList<Gruppe>  grupperList;

    @FXML
    private GridPane reisendeView;

    @FXML
    private TextField reisendeNavnField;

    @FXML
    private Slider reisendeAlderField;

    @FXML
    private ChoiceBox<Gruppe> gruppeVelger;

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

   // Kjør på med en observable list her så er det go!     
   //     gruppeVelger.setItems(this.flight.getGruppeProperties);
        
   
   
        // Set rett verdi på radibutton dersom verdien finnes
        try {
            if(this.reisende.getKjonn().toString().equals(reisendeKjonnRadioMANN.getText()) && this.reisende.getKjonn().toString() != null) {
                this.reisendeKjonnRadioMANN.setSelected(true);
            } else {
                this.reisendeKjonnRadioKVINNE.setSelected(true);
            }
        } catch (NullPointerException e) {
                System.out.println("Håper noen finner ut av kjønnet til slutt..");
        }
                
        // Set rett verdi på radiobutton dersom betalingsObjektet er opprettet
        try {
            if(this.reisende.getBetaling().toString() != null) {
                if(this.reisende.getBetaling().toString().equals(reisendeBetalingCheckCash.getText()) && this.reisende.getBetaling().toString() != null) {
                    this.reisendeBetalingCheckCash.setSelected(true);
                } else {
                    this.reisendeBetalingCheckKort.setSelected(true);
                }
            }
        } catch (NullPointerException e) {
                System.out.println("Kastet hansken, verdien er ikke satt");
        }


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

    void setGroupList(ObservableList<Gruppe> grupper) {
        this.grupperList = grupper;
        gruppeVelger.setItems(this.grupperList);
    }
}
