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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private MenuItem menuFileExit;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField antallPlasserTextBox;
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
  
    @FXML
    private void handleAddReisendeAction(ActionEvent event) throws IOException {
        Reisende reisende = new Reisende();
        reisende.setNavn("-");
        
        this.flight.addReisende(reisende);
        this.showReisendeDialog(reisende);
    }
 
    @FXML
    private void handleEditReisendeAction(ActionEvent event) throws IOException {
        Reisende rowData = this.flight.getReisendeByIndex(this.passengerTable.getSelectionModel().getSelectedIndex());
        this.showReisendeDialog(rowData);
    }
    
    @FXML
    private void handleDeleteReisendeAction(ActionEvent event) {
        this.flight.removeReisende(this.flight.getReisendeByIndex(this.passengerTable.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void handleImportFromFlightAction(ActionEvent event) {
    }

    
    
    @FXML
    private void handleAddGruppeAction(ActionEvent event) throws IOException {
        Gruppe gruppe = new Gruppe();
        gruppe.setGruppeKode("-");
        
        this.flight.addGruppe(gruppe);
        this.showGruppeDialog(gruppe);
    }

    @FXML
    private void handleEditGruppeAction(ActionEvent event) throws IOException {
        Gruppe rowData = this.flight.getGruppeByIndex(this.groupTable.getSelectionModel().getSelectedIndex());
        this.showGruppeDialog(rowData);
    }

    @FXML
    private void handleDeleteGruppeAction(ActionEvent event) {
        this.flight.removeGruppe(this.flight.getGruppeByIndex(this.groupTable.getSelectionModel().getSelectedIndex()));
    }

    
    void handleFlightNrChanged(InputMethodEvent event) {
 //       this.flight.setFlightNummer(this.flightnr.getText());
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;

        // Meny i topplinjen på macOS
        final String os = System.getProperty ("os.name");
        System.out.println(os);
        if (os != null && os.startsWith ("Mac")) {
          this.menuBar.useSystemMenuBarProperty().set(true);
          System.out.println("(Big)Mac-meny");
        }
 
        this.flightNummer.textProperty().bindBidirectional(this.flight.flightNummerProperty());
        this.origin.textProperty().bindBidirectional(this.flight.originProperty());
        this.destination.textProperty().bindBidirectional(this.flight.destinationProperty());
        this.departureTime.localDateTimeProperty().bindBidirectional(this.flight.departureTimeProperty());
        this.arrivalTime.localDateTimeProperty().bindBidirectional(this.flight.arrivalTimeProperty());
 //       this.antallPlasserValg.getNumber().bindBidirectional(this.flight.antallPlasserProperty());
    //    this.antallPlasserTextBox.getTextFormatter().
 

    
        IntField aa = new IntField(1,150,55);

        
        // Integer Only på Antall seter
        antallPlasserTextBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    antallPlasserTextBox.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });                
                
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


        this.groupTable.setRowFactory(tv -> {
            TableRow<Gruppe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        Gruppe rowData = row.getItem();
                        this.showGruppeDialog(rowData);
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
      controller.setFlight(this.flight);

     
      stage.show();

      return stage;
    }

    
// CRUD @ Grupper
    public Stage showGruppeDialog(Gruppe gruppe) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Gruppe.fxml"));

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setScene(new Scene((Pane) loader.load()));
      stage.setTitle("Edit Reisegruppe");

      GruppeController controller = loader.<GruppeController>getController();
      controller.setGruppe(gruppe);

      stage.show();
      
      return stage;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
    }
}
