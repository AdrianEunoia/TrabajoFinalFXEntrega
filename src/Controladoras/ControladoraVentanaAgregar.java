package Controladoras;

import SQL.Conexion;
import Ventanas.VentanaAgregar;
import Ventanas.VentanaLanding;
import Ventanas.VentanaListar;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class ControladoraVentanaAgregar implements Initializable {
    // Elementos
    @FXML
    Button idButtonAgregarTitulo, idButtonVaciarCampos, idButtonVolver, idButtonAvanzar;
    @FXML
    TextField idEditTitulo, idEditAutor, idEditLanzamiento, idEditPrecio;
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
        instancias();
    }

    private void instancias() {
        idButtonVolver.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/volver.png"))));
        idButtonVolver.setBackground(null);
        idButtonAvanzar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/avanzar.png"))));
        idButtonAvanzar.setBackground(null);
    }

    private void acciones() {
        idButtonAgregarTitulo.setOnAction(new ManejoPulsaciones());
        idButtonVaciarCampos.setOnAction(new ManejoPulsaciones());
        idButtonVolver.setOnAction(new ManejoPulsaciones());
        idButtonAvanzar.setOnAction(new ManejoPulsaciones());
    }

    private class ManejoPulsaciones implements EventHandler {
        @Override
        public void handle(Event event) {
            String titulo = idEditTitulo.getText().toString();
            String autor = idEditAutor.getText().toString();
            String fecha = idEditLanzamiento.getText().toString();
            String precio = idEditPrecio.getText().toString();
            if (event.getSource() == idButtonAgregarTitulo) {
                System.out.println("Pasando a agregar titulo");
                if(titulo.equals("") || autor.equals("") || fecha.equals("") || precio.equals("")){
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Atención");
                    dialogoWarning.setHeaderText("Todos los campos deben de estar completos");
                    dialogoWarning.showAndWait();
                }else{
                    System.out.println("Comprobando libro en BD");
                    try {
                        conexion.realizarConexion();
                        PreparedStatement preparedStatement = conexion.getConnection().prepareStatement("SELECT * " + "FROM titulos WHERE titulo = ?");
                        preparedStatement.setString(1,titulo);
                        ResultSet rs  = preparedStatement.executeQuery();
                        conexion.closeConnection();
                        if (rs.next()) {
                            Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                            dialogoError.setTitle("Atención");
                            dialogoError.setHeaderText("Ya hay un titulo registrado con ese nombre");
                            dialogoError.showAndWait();
                        }else{
                            conexion.realizarConexion();
                            PreparedStatement preparedStatementInsert = conexion.getConnection().prepareStatement("INSERT INTO titulos () " + "VALUES (?,?,?,?)");
                            preparedStatementInsert.setString(1,titulo);
                            preparedStatementInsert.setString(2,autor);
                            preparedStatementInsert.setString(3,fecha);
                            preparedStatementInsert.setString(4,precio);
                            preparedStatementInsert.executeUpdate();
                            conexion.closeConnection();
                            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                            dialogoInfo.setTitle("Exito");
                            dialogoInfo.setHeaderText("Usuario registrado con exito!");
                            dialogoInfo.showAndWait();
                            vaciarCampos();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }else if((event.getSource() == idButtonVaciarCampos)){
                System.out.println("Pasando a vaciar campos");
                vaciarCampos();
            }else if((event.getSource() == idButtonVolver)){
                VentanaLanding ventanaLanding = new VentanaLanding();
                ventanaLanding.show();
                Stage stage = (Stage) idButtonVolver.getScene().getWindow();
                stage.close();
            }else if((event.getSource() == idButtonAvanzar)){
                System.out.println("Avanzando");
                VentanaListar ventanaListar = new VentanaListar();
                ventanaListar.show();
                Stage stage = (Stage) idButtonAvanzar.getScene().getWindow();
                stage.close();
            }
        }
    }
    // Conexion
    public void setConection(Conexion conexion){
        this.conexion = conexion;
    }
    // Vaciar campos
    public void vaciarCampos(){
        idEditTitulo.setText("");
        idEditAutor.setText("");
        idEditLanzamiento.setText("");
        idEditPrecio.setText("");
    }
}
