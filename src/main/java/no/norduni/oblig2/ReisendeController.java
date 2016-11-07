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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
