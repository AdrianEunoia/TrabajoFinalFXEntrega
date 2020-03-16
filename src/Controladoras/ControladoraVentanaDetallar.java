package Controladoras;

import SQL.Conexion;
import Ventanas.VentanaAgregar;
import Ventanas.VentanaListar;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladoraVentanaDetallar implements Initializable {
    // Elementos
    @FXML
    BorderPane borderPane;
    @FXML
    Button idButtonVolver, idButtonActualizar, idButtonDefecto, idButtonBuscar;
    @FXML
    Label idLabelTitulo, idLabelAutor, idLabelFecha, idLabelPrecio;
    @FXML
    TextField idEditBuscar, idEditTitulo, idEditAutor, idEditLanzamiento, idEditPrecio;

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
        idButtonBuscar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/buscar.png"))));
        idButtonBuscar.setBackground(null);
    }

    private void acciones() {
        idButtonVolver.setOnAction(new ManejoPulsaciones());
        idButtonActualizar.setOnAction(new ManejoPulsaciones());
        idButtonDefecto.setOnAction(new ManejoPulsaciones());
        idButtonBuscar.setOnAction(new ManejoPulsaciones());
    }
    private class ManejoPulsaciones implements EventHandler {
        @Override
        public void handle(Event event) {
            if (event.getSource() == idButtonVolver) {
                System.out.println("Pasando a agregar titulo");
                VentanaListar ventanaListar = new VentanaListar();
                ventanaListar.show();
                Stage stage = (Stage) idButtonVolver.getScene().getWindow();
                stage.close();
            }else if(event.getSource() == idButtonBuscar){
                System.out.println("Pasando a buscar titulo");
                String nombreBuscar = idEditBuscar.getText();
                if(nombreBuscar.equals("")){
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Atención");
                    dialogoWarning.setHeaderText("Debes introducir el nombre del título para buscarlo...");
                    dialogoWarning.showAndWait();
                }else{
                    System.out.println("Consultando título");
                    try {
                        conexion.realizarConexion();
                        PreparedStatement preparedStatement = conexion.getConnection().prepareStatement("SELECT * " + "FROM titulos WHERE titulo = ?");
                        preparedStatement.setString(1,nombreBuscar);
                        ResultSet rs  = preparedStatement.executeQuery();
                        conexion.closeConnection();
                        if (rs.next()) {
                            idLabelTitulo.setText(rs.getString("titulo"));
                            idLabelAutor.setText(rs.getString("autor"));
                            idLabelFecha.setText(rs.getString("fecha"));
                            idLabelPrecio.setText(rs.getString("precio"));
                        }else{
                            Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                            dialogoWarning.setTitle("Atención");
                            dialogoWarning.setHeaderText("No hay títulos en nuestra biblioteca con ese nombre");
                            dialogoWarning.showAndWait();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }else if(event.getSource() == idButtonActualizar){
                System.out.println("Pasando a actualizar titulo");
                if(idEditAutor.getText().equals("") || idEditLanzamiento.getText().equals("") || idEditPrecio.getText().equals("") || idEditTitulo.getText().equals("")){
                    Alert dialogoWarning = new Alert(Alert.AlertType.ERROR);
                    dialogoWarning.setTitle("Error");
                    dialogoWarning.setHeaderText("Todos los campos deben de estar completos para su actualización");
                    dialogoWarning.showAndWait();
                }else{
                    conexion.realizarConexion();
                    PreparedStatement preparedStatementInsert = null;
                    try {
                        preparedStatementInsert = conexion.getConnection().prepareStatement("UPDATE titulos SET titulo = ?, autor = ?, fecha = ?, precio = ? WHERE titulo = ?");
                        preparedStatementInsert.setString(1,idEditTitulo.getText().toString());
                        preparedStatementInsert.setString(2,idEditAutor.getText().toString());
                        preparedStatementInsert.setString(3,idEditLanzamiento.getText().toString());
                        preparedStatementInsert.setInt(4, Integer.parseInt(idEditPrecio.getText()));
                        preparedStatementInsert.setString(5,idLabelTitulo.getText().toString());
                        preparedStatementInsert.executeUpdate();
                        conexion.closeConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                    dialogoInfo.setTitle("Exito");
                    dialogoInfo.setHeaderText("Título actualizado con exito!");
                    dialogoInfo.showAndWait();
                }
            }else if(event.getSource() == idButtonDefecto){
                System.out.println("Pasando a completar titulo");
                if(idEditTitulo.getText().equals("")){
                    idEditTitulo.setText(idLabelTitulo.getText());
                }if(idEditAutor.getText().equals("")){
                    idEditAutor.setText(idLabelAutor.getText());
                }if(idEditPrecio.getText().equals("")){
                    idEditPrecio.setText(idLabelPrecio.getText());
                }if(idEditLanzamiento.getText().equals("")){
                    idEditLanzamiento.setText(idLabelFecha.getText());
                }
            }
        }
    }
    // Conexion
    public void setConection(Conexion conexion){
        this.conexion = conexion;
    }
}
