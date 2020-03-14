package Controladoras;

import SQL.Conexion;
import Ventanas.VentanaLogin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladoraVentanaRegistro implements Initializable {
    // Elementos
    @FXML
    Button idButtonVolverLogin, idButtonRegistrarRegistro;
    @FXML
    TextField editUsuarioRegistro;
    @FXML
    PasswordField editPassRegistro, editPassDosRegistro;
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
        idButtonVolverLogin.setOnAction(new ManejoPulsaciones());
        idButtonRegistrarRegistro.setOnAction(new ManejoPulsaciones());
    }

    class ManejoPulsaciones implements EventHandler {
        @Override
        public void handle(Event event) {
            if (event.getSource() == idButtonVolverLogin) {
                System.out.println("Pulsado para volver a login");
                try {
                    VentanaLogin ventanaLogin = new VentanaLogin();
                    ventanaLogin.show();
                    Stage stage = (Stage) idButtonVolverLogin.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (event.getSource() == idButtonRegistrarRegistro) {
                System.out.println("Pulsado para registrar un usuario");
                String nombreUser = editUsuarioRegistro.getText().toString();
                String passUserUno = editPassRegistro.getText().toString();
                String passUserDos = editPassDosRegistro.getText().toString();
                if(nombreUser.equals("") || passUserDos.equals("") || passUserDos.equals("")){
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Atención");
                    dialogoWarning.setHeaderText("Todos los campos del registro deben de estar completos");
                    dialogoWarning.showAndWait();
                }else{
                    if(passUserDos.equals(passUserUno)){
                        System.out.println("Procediendo al registro");
                            try {
                                conexion.realizarConexion();
                                PreparedStatement preparedStatement = conexion.getConnection().prepareStatement("SELECT * " + "FROM usuariosregistro WHERE nombre = ?");
                                preparedStatement.setString(1,nombreUser);
                                ResultSet rs  = preparedStatement.executeQuery();
                                conexion.closeConnection();
                                if (rs.next()) {
                                    Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                                    dialogoError.setTitle("Atención");
                                    dialogoError.setHeaderText("Ya hay un usuario registrado con ese nombre");
                                    dialogoError.showAndWait();
                                }else{
                                    conexion.realizarConexion();
                                    PreparedStatement preparedStatementInsert = conexion.getConnection().prepareStatement("INSERT INTO usuariosregistro () " + "VALUES (?,?)");
                                    preparedStatementInsert.setString(1,editUsuarioRegistro.getText().toString());
                                    preparedStatementInsert.setString(2,editPassRegistro.getText().toString());
                                    preparedStatementInsert.executeUpdate();
                                    conexion.closeConnection();
                                    Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                                    dialogoInfo.setTitle("Exito");
                                    dialogoInfo.setHeaderText("Usuario registrado con exito!");
                                    dialogoInfo.showAndWait();
                                }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                        dialogoError.setTitle("Atención");
                        dialogoError.setHeaderText("Las contraseñas introducidas no coinciden");
                        dialogoError.showAndWait();
                    }
                }
            }
        }
    }
    // Conexion
    public void setConection(Conexion conexion){
        this.conexion = conexion;
    }
}
