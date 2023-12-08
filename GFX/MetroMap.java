package GFX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MetroMap extends Application {

    private static final Map<String, EstacionData> estaciones = new HashMap<>();
    private static List<String> listaEstaciones = new ArrayList<>();
    private static Label pesoTotal;
    private Rectangle cuadradoAmarillo;
        private static final List<String> ESTACIONES_ORDENADAS = Arrays.asList(
        "Ampere Victor Hugo", "Bellecour", "Brotteaux", "Charpennes Charles Hernu",
        "Cordeliers", "Croix-Paquet", "Croix-Rousse", "Cusset", "Debourg",
        "Flachet", "Foch", "Garibaldi", "Gare de Vaise", "Gare de Venissieux",
        "Gare Part-Dieu Vivier Merle", "Grange Blanche", "Gratte-Ciel", 
        "Guillotiere", "Henon", "Hotel de Ville Louis Pradel", "Jean Mace", 
        "Laennec", "Laurent Bonnevay Astroballe", "Massena", "Mermoz Pinel", 
        "Monplaisir Lumiere", "Oullins Gare", "Parilly", "Perrache", 
        "Place Guichard Bourse du Travail", "Place Jean Jaures", "Republique Villeurbanne",
        "Sans-Souci", "Saxe Gambetta", "Stade de Gerland", "Valmy", 
        "Vaulx-en-Velin La Soie", "Vieux Lyon Cathedrale St. Jean"
    );
    
    public static void addEstacion(String nombre, EstacionData data) {
        estaciones.put(nombre, data);
    }

    public static EstacionData getEstacion(String estacion) {
        if (estaciones.containsKey(estacion))
            return estaciones.get(estacion);
        return null;
    }
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 800);

        // PANTALLA INICIAL Y POSICIONES
        VBox layout = crearDesplegable(primaryStage, root);
        layout.setLayoutX(1080 / 2);
        layout.setLayoutY(700 / 2);
        root.getChildren().add(layout);

        primaryStage.setTitle("Mapa del Metro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox crearDesplegable(Stage primaryStage, Pane root) {
        Label lblInicio = new Label("Inicio:");
        Label lblDestino = new Label("Destino:");
        
        ComboBox<String> estacionInicio = new ComboBox<>();
        ComboBox<String> estacionDestino = new ComboBox<>();
        estacionInicio.getItems().addAll(ESTACIONES_ORDENADAS);
        estacionDestino.getItems().addAll(ESTACIONES_ORDENADAS);

        // Comienza la simulacion
        Button btnIniciar = new Button("Iniciar Recorrido");
        btnIniciar.setOnAction(e -> {
            listaEstaciones.clear();
            listaEstaciones.add(estacionInicio.getValue()); // con getValue se obtiene el String
            listaEstaciones.add(estacionDestino.getValue()); // Prueba de movimiento, pero eliminar
            
            // **************************************
            // LLAMADA AL ALGORITMO DEBE DEVOLVER UNA LISTA DE ESTACIONES Y GUARDARLA EN estaciones

            // **************************************

            iniciarSimulacion(root);
        });

        // Agregar componentes a la ventana
        VBox layout = new VBox(10);
            layout.getChildren().addAll(
        new HBox(17, lblInicio, estacionInicio),
        new HBox(5, lblDestino, estacionDestino),
        btnIniciar
    );

        return layout;
    }

    private void iniciarSimulacion(Pane root) {
        root.getChildren().clear();

        new MetroLineA(root);
        new MetroLineB(root);
        new MetroLineC(root);
        new MetroLineD(root);

        // PESO DEL CAMINO OPTIMO
        pesoTotal = new Label("Peso total: 0");
        pesoTotal.setLayoutX(1280 - 150);
        pesoTotal.setLayoutY(40);
        root.getChildren().add(pesoTotal);

        // TREN
        cuadradoAmarillo = new Rectangle(12, 12, Color.YELLOW);
        cuadradoAmarillo.setX(estaciones.values().iterator().next().getX());
        cuadradoAmarillo.setY(estaciones.values().iterator().next().getY());
        root.getChildren().add(cuadradoAmarillo);

        actualizarContador();
    }

    private void actualizarContador() {
        if (!listaEstaciones.isEmpty()) {
            avanzaEstacion(0);
        }
    }
    
    private void avanzaEstacion(int indice) {
        if (indice < listaEstaciones.size() - 1) {
            String estacionA = listaEstaciones.get(indice);
            String estacionB = listaEstaciones.get(indice + 1);
            
            moverCuadrado(estacionA, estacionB, () -> {
                sumarPesoConexion(estacionA, estacionB);
                avanzaEstacion(indice + 1);
            });
        }
    }
    

public static void sumarPesoConexion(String estacionA, String estacionB) {
    EstacionData dataEstacionA = estaciones.get(estacionA);
    if (dataEstacionA != null) {
        int pesoConexion = dataEstacionA.getPesoHacia(estacionB);
        System.out.println("Peso de " + estacionA + " a " + estacionB + ": " + pesoConexion);

        int pesoActual = Integer.parseInt(pesoTotal.getText().split(": ")[1]);
        System.out.println("Peso actual antes de sumar: " + pesoActual);

        pesoTotal.setText("Peso Total: " + (pesoActual + pesoConexion));

        System.out.println("Nuevo peso total: " + (pesoActual + pesoConexion));
    }
}

private void moverCuadrado(String estacionA, String estacionB, Runnable onFinish) {
    EstacionData dataEstacionA = estaciones.get(estacionA);
    EstacionData dataEstacionB = estaciones.get(estacionB);
    if (dataEstacionA != null && dataEstacionB != null) {
        // Camino de la animacion
        Line path = new Line(dataEstacionA.getX(), dataEstacionA.getY(), dataEstacionB.getX(), dataEstacionB.getY());

        // Efectos de la transicion del tren
        PathTransition transition = new PathTransition();
        transition.setNode(cuadradoAmarillo);
        transition.setPath(path);
        transition.setDuration(Duration.seconds(5));
        transition.setCycleCount(1);

        transition.setOnFinished(e -> onFinish.run());

        transition.play();
    }
}


    public static void main(String[] args) {



        launch(args);


        // Por si se necesita revisar el listado de conexiones de cada estacion
        // for (Map.Entry<String, EstacionData> estacionEntry : estaciones.entrySet()) {
        //     String nombreEstacion = estacionEntry.getKey();
        //     EstacionData data = estacionEntry.getValue();

        //     System.out.println("Estación: " + nombreEstacion);
        //     System.out.println("Coordenadas: (" + data.getX() + ", " + data.getY() + ")");
            
        //     System.out.println("Conexiones:");
        //     for (Map.Entry<String, Integer> conexion : data.getPesosConexiones().entrySet()) {
        //         System.out.println("  Hacia " + conexion.getKey() + " - Peso: " + conexion.getValue());
        //     }
        //     System.out.println();
        // }
    }
}
