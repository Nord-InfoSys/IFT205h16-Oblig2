/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.norduni.oblig3;

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
import javafx.scene.control.ContextMenu;
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
    private ObservableList<Flight> flighter;

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
    private ContextMenu reisendeContext;
    @FXML
    private MenuItem flyttReisendeMeny;
    @FXML
    private ContextMenu gruppeContext;
    @FXML
    private MenuItem flyttGruppeMeny;
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
        
        try {
            this.flight.addReisende(reisende);
            this.showReisendeDialog(reisende);
        } catch (Exception ex) {
            Logger.getLogger(FlightController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            Stage foo = this.showReisendeSearchDialog();
        } catch (IOException ex) {
            Logger.getLogger(FlightController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        this.antallPlasserTextBox.setText(this.flight.getAntallPlasser().toString());
 

    
        //IntField aa = new IntField(1,150,55);

        
        // Integer Only på Antall seter
        antallPlasserTextBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    antallPlasserTextBox.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Oppdater antall seter når feltet mister fokus
        antallPlasserTextBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d*")) {
                    FlightController.this.flight.setAntallPlasser(Integer.parseInt(newValue) );
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
    
    private MenuItem getFlyttReisendeToFlightMenuItem(Flight f) {
        MenuItem m = new MenuItem(f.getFlightNummer());    
        m.setOnAction(e ->{
            try {
                Reisende r = (Reisende)passengerTable.getSelectionModel().getSelectedItem();
                this.flight.removeReisende(r);
                try {
                    Gruppe g = r.getGruppe();
                    g.delReisende(r);
                    r.setGruppe(null);
                } catch (Exception ex) {
                    System.out.println("Har ingen gruppe.");
                }
                f.addReisende(r);
            } catch (Exception ex) {
                Logger.getLogger(FlightController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return m;
    }
    
    private MenuItem getFlyttGruppeToFlightMenuItem(Flight f) {
        MenuItem m = new MenuItem(f.getFlightNummer());    
        m.setOnAction(e ->{
            try {
                Gruppe g = (Gruppe)this.groupTable.getSelectionModel().getSelectedItem();
                this.flight.removeGruppe(g);
                f.addGruppe(g);
                for (Reisende r : g.getReisende()) {
                    this.flight.removeReisende(r);
                    f.addReisende(r);
                }
            } catch (Exception ex) {
                Logger.getLogger(FlightController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return m;
        
    }


// CRUD @ Reisende
    public Stage showReisendeDialog(Reisende reisende) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reisende.fxml"));

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setScene(new Scene((Pane) loader.load()));
      stage.setTitle("Edit Reisende");
     
      ReisendeController controller = loader.<ReisendeController>getController();
      
      controller.setFlight(this.flight);
      controller.setReisende(reisende);
     
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

    // CRUD @ Reisende
    public Stage showReisendeSearchDialog() throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReisendeSearch.fxml"));

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setScene(new Scene((Pane) loader.load()));
      stage.setTitle("Søk passnummer");
      
      ReisendeSearchController controller = loader.<ReisendeSearchController>getController();
      controller.setFlight(flight);
      controller.setFlightList(flighter);
      
      stage.show();

      return stage;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setFlighter(ObservableList<Flight> flighter) {
        this.flighter = flighter;
        
        //contextmeny, passasjer
        for(Flight f : flighter) {
            if(!f.equals(this.flight)) {
                reisendeContext.getItems().add(this.getFlyttReisendeToFlightMenuItem(f));
                System.out.println("Legger til meny for " + f.getFlightNummer());
            }
        }
        
        //contextmeny grupper
        for(Flight f : flighter) {
            if(!f.equals(this.flight)) {
                gruppeContext.getItems().add(this.getFlyttGruppeToFlightMenuItem(f));
                System.out.println("Legger til meny for " + f.getFlightNummer());
            }
        }
    }

}
