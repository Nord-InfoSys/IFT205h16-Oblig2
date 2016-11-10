/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.scene.control.LocalDateTimeTextField;


/**
 *
 * @author bubbaJ
 */
public class FlightController {

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

        // Set CellValueFactory for each column.
        // The ID on each column is named the same as the property names on the model object.
        // PropertyValueFactory will try to fetch the property by .nameProperty() automagically.
        // It will revert to .getName() if the model does not have .nameProperty()

        
        this.passengerTable.setRowFactory(tv -> {
            TableRow<Reisende> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        Reisende rowData = row.getItem();
                        this.showReisendeDialog(rowData);
                        System.out.println(rowData);
                    } catch (IOException ex) {
                        Logger.getLogger(FlightController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });


        // Bind Passenger List to this.passengerTable
        this.passengerTable.setItems(this.flight.getReisende());

        // Fyll inn data i radene
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


// CRUD @ Reisende
    public Stage showReisendeDialog(Reisende reisende) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reisende.fxml"));

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setScene(new Scene((Pane) loader.load()));
      stage.setTitle("Edit Reisende");

      ReisendeController controller = loader.<ReisendeController>getController();
      controller.setReisende(reisende);

      stage.show();

      return stage;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
