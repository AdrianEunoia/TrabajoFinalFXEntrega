package Controladoras;

import Ventanas.VentanaLogin;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import Ventanas.VentanaLogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladoraVentanaSplash implements Initializable {
    // Elementos
    @FXML
    ImageView imageSplash;
    @FXML
    ProgressBar progresBar;
    //FadeTransition fadeTransition;
    Task tareaSecundaria;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instancias();
        progresBar.progressProperty().bind(tareaSecundaria.progressProperty());
        FadeTransition transition = new FadeTransition(Duration.seconds(2),imageSplash);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.play();

        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Thread(tareaSecundaria).start();
            }
        });

        tareaSecundaria.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    VentanaLogin ventanaLogin = new VentanaLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage ventana = (Stage) imageSplash.getScene().getWindow();
                ventana.hide();
            }
        });
    }

    private void instancias() {
        tareaSecundaria = new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    updateProgress(i, 100);
                    updateMessage("cambiado");
                    Thread.sleep(20);
                }
                return null;
            }
        };
    }
}