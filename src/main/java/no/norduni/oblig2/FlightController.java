/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import javafx.fxml.FXML ;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField ;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author mortenj
 */
public class FlightController {

    private Flight flight;
    
    @FXML
    private GridPane flightView;

    @FXML
    private TextField flightnr;
    
    @FXML
    private Slider antallPlasser;
    
    @FXML
    void handleFlightNrChanged(InputMethodEvent event) {
 //       this.flight.setFlightNummer(this.flightnr.getText());
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        this.flightnr.textProperty().bindBidirectional(this.flight.flightNummerProperty());
        //this.flight.flightNummerProperty().bindBidirectional(this.flightnr.textProperty() );
        this.antallPlasser.valueProperty().bindBidirectional(this.flight.antallPlasserProperty());
        //this.flight.antallPlasserProperty().bindBidirectional(this.antallPlasser.valueProperty());
    }
    

}
