package no.norduni.oblig2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FlightListController implements Initializable {

    ObservableList<Flight> flighter = FXCollections.observableArrayList();
    
    String saveFileName = "amadeus_190565";
    
    @FXML
    private BorderPane BorderPane;

    @FXML
    private MenuItem menuFileSave;

    @FXML
    private MenuItem menuFileLoad;

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
        flight.setDepartureTime(LocalDateTime.now());
        flight.setArrivalTime(LocalDateTime.now().plusHours(1));
        flight.setDuration(Duration.ofSeconds(3600));
        flight.setAntallPlasser(89);
        this.flighter.add(flight);
        this.showFlightDialog(flight);
    }
    
    @FXML
    private void handleDeleteFlightAction(ActionEvent event) throws IOException {
        flighter.remove(flightTableView.getSelectionModel().getSelectedIndex());
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText("Hello World!");
        
        Flight flyvning = new Flight();
        flyvning.setFlightNummer("SK3435");
        flyvning.setDestination("TRD");
        flyvning.setOrigin("OSL");
        flyvning.setDepartureTime(LocalDateTime.now());
        flyvning.setDuration(Duration.ofSeconds(3700));
        flyvning.setArrivalTime(LocalDateTime.now().plusHours(2));
        flyvning.setDuration(Duration.ofSeconds(7200));
        flyvning.setAntallPlasser(44);        
        this.flighter.add(flyvning);

        for(Flight f: this.flighter) {
            f.antallPlasserProperty().setValue(f.getAntallPlasser()+1);
            System.out.println("Antall Plasser is now: "+f.antallPlasserProperty().getValue());
        }
        //this.flightTableView.setItems(null);
        //this.flightTableView.setItems(flighter);
    }

    private void loadFlightsFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(this.saveFileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        this.flighter = javafx.collections.FXCollections.observableArrayList();
        this.flighter.setAll((List<Flight>) ois.readObject());
        // Re-bind the TableView
        this.flightTableView.setItems(this.flighter);
    }
    
    @FXML
    private void handleFileLoadAction(ActionEvent event) {
        try {
            this.loadFlightsFromFile();
            System.out.println("Åpning komplett...");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Åpning feilet...");
        }
    }

    @FXML
    private void handleFileSaveAction(ActionEvent event) {
        try {
            ArrayList flightList = new ArrayList(this.flighter);
            FileOutputStream fout = new FileOutputStream(this.saveFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(flightList);
            System.out.println("Lagring komplett...");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Lagring feilet...");
        }
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
        stage.setTitle("Edit Flight");

        FlightController controller = loader.<FlightController>getController();
        controller.setFlight(flight);
        controller.setFlighter(flighter);


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
        


        this.flightTableView.setRowFactory( tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        Flight rowData = row.getItem();
                        this.showFlightDialog(rowData);
                        System.out.println(rowData);
                    } catch (IOException ex) {
                        Logger.getLogger(FlightListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });


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
        try {
            this.loadFlightsFromFile();
            System.out.println("Åpning komplett...");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Åpning feilet... Laster inn default data...");

            Flight flyvning = new Flight();
            flyvning.setFlightNummer("WF747");
            flyvning.setDestination("MQN");
            flyvning.setOrigin("TRD");
            flyvning.setDepartureTime(LocalDateTime.now());
            flyvning.setArrivalTime(LocalDateTime.now().plusMinutes(5));
            flyvning.setDuration(Duration.ofSeconds(300));
            flyvning.setAntallPlasser(35);        


            Reisende r1 = new Reisende();
            r1.setNavn("Ola Dunk");
            r1.setKjonn(Kjonn.MANN);
            r1.setAlder(34);
            r1.setPassnr("87654321");

            Reisende r2 = new Reisende();
            r2.setNavn("Kari Dunk");
            r2.setKjonn(Kjonn.KVINNE);
            r2.setAlder(33);
            r2.setPassnr("2344234");

            Reisende r3 = new Reisende();
            r3.setNavn("P. Mester Fix");
            r3.setKjonn(Kjonn.MANN);
            r3.setAlder(44);
            r3.setPassnr("8976543");

            Gruppe g1 = new Gruppe();
            g1.setGruppeKode("42");
            g1.addReisende(r1);
            g1.addReisende(r2);

            Gruppe g2 = new Gruppe();
            g2.setGruppeKode("LænsMainn");
            g2.addReisende(r3);

            try {
                flyvning.addReisende(r1);
                flyvning.addReisende(r2);
                flyvning.addReisende(r3);
            } catch (Exception ex) {
                Logger.getLogger(FlightListController.class.getName()).log(Level.SEVERE, null, ex);
            }

            r1.setBetaling(new Betaling(Betalingsmetode.CASH));
            r2.setBetaling(new Betaling(Betalingsmetode.CASH));
            r3.setBetaling(new Betaling(Betalingsmetode.KREDITT));
            
            flyvning.addGruppe(g1);
            flyvning.addGruppe(g2);

            flighter.add(flyvning);

        }
    }

    public void updateFlightList() {
        // Workaround for å oppdatere tableView
        ObservableList<TableColumn> columns = flightTableView.getColumns();
        TableColumn c = columns.get(0);
        c.setVisible(false);
        c.setVisible(true);
    }
    
    @FXML
    private void magicHoverAction(MouseEvent event) {
        //this.clickMeButton.disableProperty().setValue(Boolean.TRUE);
    }

}
