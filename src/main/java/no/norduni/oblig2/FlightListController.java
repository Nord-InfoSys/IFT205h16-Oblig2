package no.norduni.oblig2;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FlightListController implements Initializable {

    ObservableList<Flight> flighter = FXCollections.observableArrayList();
    
    @FXML
    private BorderPane BorderPane;

    @FXML
    private MenuItem menuFileExit;

    @FXML
    private Label label;

    @FXML
    private Button clickMeButton;
    
    @FXML
    private Button addFlightButton;

    @FXML
    private TableView flightTableView;
    
    @FXML
    private void handleAddFlightAction(ActionEvent event) throws IOException {
        Flight flight = new Flight();
        flight.setFlightNummer("ABC123");
        flight.setDestination("OSL");
        flight.setOrigin("TRD");
        flight.setDepartureTime(ZonedDateTime.now());
        flight.setDuration(Duration.ofSeconds(3700));
        flight.setAntallPlasser(89);
        this.flighter.add(flight);
        this.showFlightDialog(flight);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText("Hello World!");
        
        Flight flyvning = new Flight();
        flyvning.setFlightNummer("SK3435");
        flyvning.setDestination("TRD");
        flyvning.setOrigin("OSL");
        flyvning.setDepartureTime(ZonedDateTime.now());
        flyvning.setDuration(Duration.ofSeconds(3700));
        flyvning.setAntallPlasser(44);        
        this.flighter.add(flyvning);

        for(Flight f: this.flighter) {
            f.antallPlasserProperty().setValue(f.getAntallPlasser()+1);
            System.out.println("Antall Plasser is now: "+f.antallPlasserProperty().getValue());
        }
        //this.flightTableView.setItems(null);
        //this.flightTableView.setItems(flighter);
    }

    @FXML
    private void handleFileExitAction(ActionEvent event) {
        System.out.println("You clicked exit");
        System.exit(0);
    }

    public Stage showFlightDialog(Flight flight) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Flight.fxml"));

      Stage stage = new Stage(StageStyle.DECORATED);
      stage.setScene(new Scene((Pane) loader.load()));

      FlightController controller = loader.<FlightController>getController();
      controller.setFlight(flight);

      stage.show();

      return stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MenuBar menuBar = (MenuBar) this.BorderPane.getTop();
        
        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac"))
          menuBar.useSystemMenuBarProperty().set(true);
        
        // Create some default data
        this.bootstrapModelData();
        
        this.flightTableView.setItems(this.flighter);
        
        ObservableList<TableColumn> col = this.flightTableView.getColumns();
        for(TableColumn c : col) {
            switch(c.getId()) {
//                case "antallPlasser":
//                    c.setCellValueFactory(new PropertyValueFactory<Flight, SimpleIntegerProperty>("antallPlasser"));
//                    break;
                default:
                    c.setCellValueFactory(new PropertyValueFactory(c.getId()));
            }
        }
    }
    
    private void bootstrapModelData() {

        Flight flyvning = new Flight();
        flyvning.setFlightNummer("WF747");
        flyvning.setDestination("MQN");
        flyvning.setOrigin("TRD");
        flyvning.setDepartureTime(ZonedDateTime.now());
        flyvning.setDuration(Duration.ofSeconds(3700));
        flyvning.setAntallPlasser(35);        
 
        
        Reisende r1 = new Reisende("Ola Dunk", Kjonn.MANN, 34, "87654321");
        Reisende r2 = new Reisende("Kari Dunk", Kjonn.KVINNE, 33, "2344234");
 
        Gruppe g1 = new Gruppe();
        g1.setGruppeKode("42");
        g1.addReisende(r1);
        g1.addReisende(r2);
        
        flyvning.addReisende(r1);
        flyvning.addReisende(r2);
        
        flyvning.addGruppe(g1);
        
        flighter.add(flyvning);

    }
}
