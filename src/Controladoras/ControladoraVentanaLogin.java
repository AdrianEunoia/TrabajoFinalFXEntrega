package Controladoras;

import SQL.Conexion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import SQL.Conexion;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControladoraVentanaLogin implements Initializable{
    // Elementos
    @FXML
    Button idButtonLogear, idButtonRegistrar;
    @FXML
    TextField editTextUsuario;
    @FXML
    PasswordField editTextPassword;
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
        idButtonLogear.setOnAction(new ManejoPulsaciones());
        idButtonRegistrar.setOnAction(new ManejoPulsaciones());
    }

    class ManejoPulsaciones implements EventHandler {

        @Override
        public void handle(Event event) {
            if (event.getSource() == idButtonRegistrar) {
                System.out.println("Pulsado para registrar usuario");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/layout_ventana_registro.fxml"));
                try {
                    borderPane.setCenter(loader.load());
                    ControladoraVentanaRegistro controladoraVentanaRegistro = loader.getController();
                    controladoraVentanaRegistro.setConection(conexion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (event.getSource() == idButtonLogear) {
                System.out.println("Pulsado para logear un usuario");
                String nombreUser = editTextUsuario.getText().toString();
                String passUser = editTextPassword.getText().toString();
                try {
                    conexion.realizarConexion();
                    PreparedStatement preparedStatement = conexion.getConnection().prepareStatement("SELECT * " + "FROM usuariosregistro WHERE nombre = ? and password = ?");
                    preparedStatement.setString(1,nombreUser);
                    preparedStatement.setString(2,passUser);
                    ResultSet rs  = preparedStatement.executeQuery();
                    conexion.closeConnection();
                    if (rs.next()) {
                        System.out.println("Login correcto");
                    }else{
                        Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                        dialogoError.setTitle("Atención");
                        dialogoError.setHeaderText("Los datos introducidos no son correctos");
                        dialogoError.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}