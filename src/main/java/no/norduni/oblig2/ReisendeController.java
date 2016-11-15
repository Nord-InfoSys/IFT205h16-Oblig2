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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
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
    private Flight flight;
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
    private Label reisendeAlderLabel;

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

        // Det vi får gratis... tar vi gratis :)
        this.reisendeNavnField.textProperty().bindBidirectional(this.reisende.navnProperty());
        this.reisendeAlderField.valueProperty().bindBidirectional(this.reisende.alderProperty());
        this.reisendePassField.textProperty().bindBidirectional(this.reisende.passnrProperty());

        
        // Add listner for year-display
        reisendeAlderLabel.setText(Integer.toString(this.reisende.getAlder()));
        this.reisendeAlderField.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                reisendeAlderLabel.setText(Integer.toString(newValue.intValue()));
            }
        });
        
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
                
        // Oppdater kjønn dersom denne endrer seg (burde være relativt sjelden, da kjønnsskifteopperasjoner ikke er _veldig_ vanlig.
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


        // Oppdater betalingsmetode dersom denne endres.
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

        

        this.gruppeVelger.setValue(reisende.getGruppe());
        // Set rett verdi i dropDown dersom gruppe er satt
//        try {
//            if(this.reisende.getGruppe() != null) {
//            //    gruppeVelger.setValue(this.reisende.getGruppe());
//                gruppeVelger.getSelectionModel().selectFirst();
//             }
//        } catch (NullPointerException e) {
//                System.out.println("FucMei" + e);
//        }


        // EvesDropping @ gruppeValg
        gruppeVelger.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old_gruppe, Number new_gruppe) {
                if (gruppeVelger.getSelectionModel().selectedIndexProperty() != null) {

                    // Oppdater den reisende med rett gruppe
                    ReisendeController.this.reisende.setGruppe(ReisendeController.this.flight.getGruppeByIndex((Integer) new_gruppe));
                    // System.out.println(gruppeVelger.getItems().get((Integer) new_gruppe));
                }
            }
        });      
        
        
        

    // Bind Grupper List to this.gruppeVelger
    // Hvor i "#%/¤( skal vi hente denne fra?.. det må bli fra parentFlight ??
//    this.gruppeVelger.setItems(this.flight.getGrupper());
        
    }
 
    void setFlight(Flight flight) {
        this.flight = flight;
        gruppeVelger.setItems(this.grupperList);
        this.setGroupList(this.flight.getGrupper());
    }
    
    void setGroupList(ObservableList<Gruppe> grupper) {
        this.grupperList = grupper;
        gruppeVelger.setItems(this.grupperList);
    }
}
