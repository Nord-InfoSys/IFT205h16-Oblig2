/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mortenj
 */
public class ReisendeSearchController implements Initializable {

    //private ObservableList<Flight> flightList;
    private Flight flight;
    private final ReisendeSearcher mySearcher = new ReisendeSearcher();
    
    @FXML
    private TextField searchField;
    @FXML
    private TableView resultsTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    public void setFlightList(ObservableList<Flight> flightList) {
        this.mySearcher.setFlighter(flightList);
        mySearcher.searchPassnr(searchField.getText());
        
        searchField.textProperty().addListener(new ChangeListener<String> (
        ) {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mySearcher.searchPassnr(newValue);
            }
        });
//        ObservableList<Flight> fl = javafx.collections.FXCollections.observableArrayList();
//        // Filter out the current flight, we're not searching our existing passenger
//        for(Flight f: flightList) {
//            if(f != this.flight) {
//                fl.add(f);
//            } else {
//                System.out.println("Legger ikke til: "+f);
//            }
//        }
//        this.mySearcher.setFlighter(fl);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        // Bind Passenger List to this.passengerTable
        this.resultsTable.setItems(this.mySearcher.getResultat());

        // Fyll inn data i radene
        ObservableList<TableColumn> col = this.resultsTable.getColumns();
        for(TableColumn c : col) {
            switch(c.getId()) {
                default:
                    c.setCellValueFactory(new PropertyValueFactory(c.getId()));
            }
        }
    }
    
    @FXML
    private void handleVelgButton(ActionEvent event) {
        Reisende r = (Reisende)this.resultsTable.getSelectionModel().getSelectedItem();
        
        if(this.flight.getReisende().contains(r)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasjon");
            alert.setHeaderText("Informasjon");
            alert.setContentText("Valgt passasjer er allerede på flyet");

            alert.showAndWait();            
        } else {
            try {
                this.flight.addReisende(r);
            } catch (Exception ex) {
                Logger.getLogger(ReisendeSearchController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        System.out.println("Search is now: "+searchField.getText());
        mySearcher.searchPassnr(searchField.getText());
    }

    @FXML
    private void handleSearchInput(ActionEvent event) {
        System.out.println("Search is now: "+searchField.getText());
        mySearcher.searchPassnr(searchField.getText());
    }
    
}
