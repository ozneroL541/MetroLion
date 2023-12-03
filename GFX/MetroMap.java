package GFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MetroMap extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Instanciar y dibujar la línea D
        new MetroLineD(root);
        
        // Instanciar y dibujar la linea C
        new MetroLineC(root);

        // Instanciar y dibujar la línea B
        new MetroLineB(root);
        
        // Instanciar y dibujar la línea A
        new MetroLineA(root);

        // Configurar la escena y el escenario
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setTitle("Mapa del Metro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
