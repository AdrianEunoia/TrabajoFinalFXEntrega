package Ventanas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaDetallar extends Stage {
    public VentanaDetallar() {
        initGUI();
    }

    private void initGUI() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../layouts/layout_ventana_detallar.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,700,500);
        this.setScene(scene);
        this.show();
    }
}
