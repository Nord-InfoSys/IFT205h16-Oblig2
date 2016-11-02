/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML ;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;


/**
 *
 * @author bubbaJ
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
    private javafx.scene.control.Button closeButton;
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
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

        // Bind Passenger List to this.passengerTable
        this.passengerTable.setItems(this.flight.getReisende());
        
        // Set CellValueFactory for each column.
        // The ID on each column is named the same as the property names on the model object.
        // PropertyValueFactory will try to fetch the property by .nameProperty() automagically.
        // It will revert to .getName() if the model does not have .nameProperty()
        ObservableList<TableColumn> col = this.passengerTable.getColumns();
        for(TableColumn c : col) {
            switch(c.getId()) {
                default:
                    c.setCellValueFactory(new PropertyValueFactory(c.getId()));
            }
        }



        // Bind Grupper List to this.groupTable
        this.groupTable.setItems(this.flight.getGrupper());
        
        ObservableList<TableColumn> col2 = this.groupTable.getColumns();
        for(TableColumn c2 : col2) {
            switch(c2.getId()) {
 /*
                case "flightNr":
                    // c2.setCellValueFactory(new PropertyValueFactory(this.flight.getFlightNummer()));  // FIX ME !!
                    //c2.setText(this.flight.getFlightNummer());
                    c2.setCellValueFactory(new PropertyValueFactory(this.flight.getGrupper().toString()));
*/
                default:
                    c2.setCellValueFactory(new PropertyValueFactory(c2.getId()));
            }
        }
    }

}
