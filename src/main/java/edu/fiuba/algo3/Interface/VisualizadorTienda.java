package edu.fiuba.algo3.Interface;

import edu.fiuba.algo3.modelo.juego.Juego;
import edu.fiuba.algo3.vista.ConstanteImagenes;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class VisualizadorTienda {

    private static VBox vbox;
    private static Text text;

    public static VBox crearPanelTienda() {
        vbox = new VBox();
        vbox.setStyle("-fx-background-color: #336699;");

        text = new Text("Defensas disponibles:");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        vbox.getChildren().addAll(text, crearIconosDefensas());
        return vbox;
    }

    private static FlowPane crearIconosDefensas() {
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(1000); // para que se apilen verticalmente
        flowPane.setVgap(30);
        flowPane.setPadding(new Insets(70, 0, 0, 65));
        Juego.getInstance().verificarConstruccionesPosibles().forEach(defensa -> {
            ImageView nuevaDefensa = null;
            try {
                nuevaDefensa = ConstanteImagenes.getImagen(defensa);
                nuevaDefensa.setFitHeight(150);
                nuevaDefensa.setFitWidth(100);
                nuevaDefensa.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("Defensa seleccionada");
                        ControladorCompra.getInstance().seleccionarDefensa(defensa);
                    }
                });
                flowPane.getChildren().add(nuevaDefensa);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return flowPane;
    }

    public static VBox updateInfo() {
        vbox.getChildren().clear();
        vbox.getChildren().addAll(text, crearIconosDefensas());
        return vbox;
    }
}
