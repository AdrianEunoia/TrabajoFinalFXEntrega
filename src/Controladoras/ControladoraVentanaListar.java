package Controladoras;

import SQL.Conexion;
import Utiles.Titulos;
import Ventanas.VentanaAgregar;
import Ventanas.VentanaDetallar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladoraVentanaListar implements Initializable {
    // Elementos
    @FXML
    BorderPane borderPane;
    @FXML
    Button idButtonVolver, idButtonAvanzar;
    @FXML
    TableView idTablaListar;
    @FXML
    TableColumn<Titulos, String> idRowTitulo;
    @FXML
    TableColumn<Titulos, String> idRowAutor;
    @FXML
    TableColumn<Titulos, String> idRowFecha;
    @FXML
    TableColumn<Titulos, Integer> idRowPrecio;

    private Conexion conexion;
    private Connection connection;
    // Tabla
    ObservableList<Titulos> listaTitulos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new Conexion();
        conexion.realizarConexion();
        connection = conexion.getConnection();
        acciones();
        instancias();
    }

    private void instancias() {
        idButtonVolver.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/volver.png"))));
        idButtonVolver.setBackground(null);
        idButtonAvanzar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/avanzar.png"))));
        idButtonAvanzar.setBackground(null);
        idRowTitulo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        idRowAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        idRowFecha.setCellValueFactory(new PropertyValueFactory<>("lanzamiento"));
        idRowPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        conexion.realizarConexion();
        try {
            PreparedStatement preparedStatement = conexion.getConnection().prepareStatement("SELECT * FROM titulos");
            ResultSet resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                listaTitulos.add(new Titulos(resultSet.getString("titulo"),resultSet.getString("autor"),resultSet.getString("fecha"),resultSet.getString("precio")));

            }
            conexion.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idTablaListar.setItems(listaTitulos);
    }

    private void acciones() {
        idButtonVolver.setOnAction(new ManejoPulsaciones());
        idButtonAvanzar.setOnAction(new ManejoPulsaciones());
    }

    private class ManejoPulsaciones implements EventHandler {
        @Override
        public void handle(Event event) {
            if((event.getSource() == idButtonVolver)){
                System.out.println("Volviendo");
                VentanaAgregar ventanaAgregar = new VentanaAgregar();
                ventanaAgregar.show();
                Stage stage = (Stage) idButtonVolver.getScene().getWindow();
                stage.close();
            }else if((event.getSource() == idButtonAvanzar)){
                System.out.println("Avanzando");
                VentanaDetallar ventanaDetallar = new VentanaDetallar();
                ventanaDetallar.show();
                Stage stage = (Stage) idButtonAvanzar.getScene().getWindow();
                stage.close();
            }
        }
    }
    // Conexion
    public void setConection(Conexion conexion){
        this.conexion = conexion;
    }
}
