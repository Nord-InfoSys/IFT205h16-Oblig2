/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML ;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
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
    private LocalTimeTextField duration;
    @FXML
    private LocalDateTimeTextField departureTime;
    @FXML
    private LocalDateTimeTextField arrivalTime;


    
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

        // Bind Passenger List to this.passengerTable
        this.passengerTable.setItems(this.flight.getReisende());
        
        ObservableList<TableColumn> col = this.passengerTable.getColumns();
        for(TableColumn c : col) {
            switch(c.getId()) {
                default:
                    c.setCellValueFactory(new PropertyValueFactory(c.getId()));
            }
        }
    }
}