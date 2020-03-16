package Controladoras;

import SQL.Conexion;
import Ventanas.VentanaAgregar;
import Ventanas.VentanaDetallar;
import Ventanas.VentanaListar;
import Ventanas.VentanaLogin;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ControladoraVentanaLanding implements Initializable {
    // Elementos
    @FXML
    Button idButtonAgregar, idButtonListar, idButtonDetallar;
    @FXML
    BorderPane borderPane;

    private Conexion conexion;
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new Conexion();
        conexion.realizarConexion();
        connection = conexion.getConnection();
        acciones();
    }

    private void acciones() {
        idButtonAgregar.setOnAction(new ManejoPulsaciones());
        idButtonDetallar.setOnAction(new ManejoPulsaciones());
        idButtonListar.setOnAction(new ManejoPulsaciones());

    }

    private class ManejoPulsaciones implements EventHandler {
        @Override
        public void handle(Event event) {
            if (event.getSource() == idButtonAgregar) {
                System.out.println("Pasando a agregar titulo");
                VentanaAgregar ventanaAgregar = new VentanaAgregar();
                ventanaAgregar.show();
                Stage stage = (Stage) idButtonAgregar.getScene().getWindow();
                stage.close();
            }else if((event.getSource() == idButtonListar)){
                System.out.println("Pasando a listar titulos");
                VentanaListar ventanaListar = new VentanaListar();
                ventanaListar.show();
                Stage stage = (Stage) idButtonListar.getScene().getWindow();
                stage.close();
            }else if((event.getSource() == idButtonDetallar)){
                System.out.println("Pasando a detallar titulo");
                VentanaDetallar ventanaDetallar = new VentanaDetallar();
                ventanaDetallar.show();
                Stage stage = (Stage) idButtonDetallar.getScene().getWindow();
                stage.close();
            }
        }
    }
    // Conexion
    public void setConection(Conexion conexion){
        this.conexion = conexion;
    }
}
