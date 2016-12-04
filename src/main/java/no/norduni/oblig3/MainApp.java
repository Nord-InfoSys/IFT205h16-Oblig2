package no.norduni.oblig3;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        MyDB foo = new MyDB();
        foo.bootStrapDB();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FlightList.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/FlightList.fxml"));
        
        Scene scene = new Scene((Pane) loader.load());
        
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("AwesomeBooking");
        stage.setScene(scene);
        
        stage.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                
                if(ov.getValue() == Boolean.TRUE) {
                    FlightListController c = loader.getController();
                    c.updateFlightList();
                    System.out.println(ov);
                }
                //System.out.println(ov);
            }
        });
        
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
   
}
