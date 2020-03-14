package Ventanas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaLogin extends Stage {
    public VentanaLogin() throws IOException {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../layouts/layout_ventana_login.fxml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root,850,500);
        this.setScene(scene);
        this.setTitle("Practica final JavaFX");
        this.show();
    }
}