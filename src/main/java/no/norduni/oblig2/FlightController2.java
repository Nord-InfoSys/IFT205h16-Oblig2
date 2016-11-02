/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.time.Duration;
import java.time.LocalDateTime;
import javafx.fxml.FXML ;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField ;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.LocalDateTimeTextField;
import jfxtras.scene.control.LocalTimeTextField;

// import org.jfxtras


/**
 *
 * @author mortenj
 */
public class FlightController2 {

    private Flight flight;
    
    @FXML
    private TextField flightNummer;

    @FXML
    private TextField origin;

    @FXML
    private TextField destination;

    @FXML
    private TextField antallPlasser;
    @FXML
    private TableView passengerTable;
    @FXML
    private TableView groupTable;
    @FXML
    private LocalDateTimeTextField departureTime;
    @FXML
    private LocalDateTimeTextField arrivalTime;


    @FXML
    private void handleDateTimeChanged(MouseEvent event) {
        this.flight.calcDuration(this.flight.getDepartureTime(), this.flight.getArrivalTime());
        System.out.println(this.flight.getDuration());
    }

   
    void handleFlightNrChanged(InputMethodEvent event) {
 //       this.flight.setFlightNummer(this.flightnr.getText());
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
 
        this.flightNummer.textProperty().bindBidirectional(this.flight.flightNummerProperty());
        this.origin.textProperty().bindBidirectional(this.flight.originProperty());
        this.destination.textProperty().bindBidirectional(this.flight.destinationProperty());
        this.departureTime.localDateTimeProperty().bindBidirectional(this.flight.departureTimeProperty());
        this.arrivalTime.localDateTimeProperty().bindBidirectional(this.flight.arrivalTimeProperty());


//        this.antallPlasser.textProperty().bindBidirectional(this.flight.antallPlasserProperty());
    }

}