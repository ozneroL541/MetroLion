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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import Alg.Station;
import Alg.alg_Astar;

public class MetroMap extends Application {

    private static final Map<String, EstacionData> estaciones = new HashMap<>();
    private static List<String> listaEstaciones = new ArrayList<>();
    private static Label pesoTotal;
    private Rectangle cuadradoAmarillo;
        private static final List<String> ESTACIONES_ORDENADAS = Arrays.asList(
        "Ampère - Victor Hugo", "Bellecour", "Brotteaux", "Charpennes - Charles Hernu",
        "Cordeliers", "Croix-Paquet", "Croix-Rousse", "Cuire", "Cusset", "Debourg",
        "Flachet", "Foch", "Garibaldi", "Gare de Vaise", "Gare de Vénissieux",
        "Gare Part-Dieu - Vivier Merle", "Grange Blanche", "Gratte-Ciel", 
        "Guillotière - Gabriel Péri", "Hénon", "Hôtel de Ville - Louis Pradel", "Jean Macé", 
        "Laënnec", "Laurent Bonnevay - Astroballe", "Masséna", "Mermoz - Pinel", 
        "Monplaisir - Lumière", "Oullins Gare", "Parilly", "Perrache", 
        "Place Guichard - Bourse du Travail", "Place Jean Jaurès", "République - Villeurbanne",
        "Sans-Souci", "Saxe - Gambetta", "Stade de Gerland", "Valmy", 
        "Vaulx-en-Velin - La Soie", "Vieux Lyon - Cathédrale Saint-Jean"
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
        primaryStage.setTitle("Metro Map");
        primaryStage.setScene(scene);

        mostrarPantallaInicio(root, primaryStage);

        primaryStage.show();
    }

    private void mostrarPantallaInicio(Pane root, Stage primaryStage) {
        VBox layout = crearDesplegable(primaryStage, root);
        layout.setLayoutX(1080 / 2);
        layout.setLayoutY(700 / 2);
        root.getChildren().clear();
        root.getChildren().add(layout);
    }

    private VBox crearDesplegable(Stage primaryStage, Pane root) {
        Label lblInicio = new Label("Departure:");
        Label lblDestino = new Label("Destination:");
        
        ComboBox<String> estacionInicio = new ComboBox<>();
        ComboBox<String> estacionDestino = new ComboBox<>();
        estacionInicio.getItems().addAll(ESTACIONES_ORDENADAS);
        estacionDestino.getItems().addAll(ESTACIONES_ORDENADAS);

        // Comienza la simulacion
        Button btnIniciar = new Button("Start Path");
        btnIniciar.setOnAction(e -> {
            listaEstaciones.clear();
            listaEstaciones.add(estacionInicio.getValue()); // con getValue se obtiene el String
            
            // **************************************
            // LLAMADA AL ALGORITMO DEBE DEVOLVER UNA LISTA DE ESTACIONES Y GUARDARLA EN estaciones
            
            Station startStation = new Station(estacionInicio.getValue());
            Station goalStation = new Station(estacionDestino.getValue());

            // Llama a aStarSearch
            if (startStation != null && goalStation != null) {
                List<Station> path = alg_Astar.aStarSearch(startStation, goalStation, null);
                listaEstaciones.clear(); // Limpia la lista actual
                for (Station station : path) {
                    listaEstaciones.add(station.getStationName()); // Asumiendo que Station tiene un método getName()
                }
            }
            // **************************************

            iniciarSimulacion(root, primaryStage);
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

    private void iniciarSimulacion(Pane root, Stage primaryStage) {
        root.getChildren().clear();

        new MetroLineA(root);
        new MetroLineB(root);
        new MetroLineC(root);
        new MetroLineD(root);

        // PESO DEL CAMINO OPTIMO
        pesoTotal = new Label("Total Weight: 0");
        pesoTotal.setLayoutX(1280 - 150);
        pesoTotal.setLayoutY(40);
        root.getChildren().add(pesoTotal);

        // TREN
        cuadradoAmarillo = new Rectangle(12, 12, Color.YELLOW);
        cuadradoAmarillo.setX(estaciones.values().iterator().next().getX());
        cuadradoAmarillo.setY(estaciones.values().iterator().next().getY());

        actualizarContador(root, primaryStage);
    }

    private void actualizarContador(Pane root, Stage primaryStage) {
        if (!listaEstaciones.isEmpty()) {
            avanzaEstacion(0, root, primaryStage);
        }
    }
    
    private void avanzaEstacion(int indice, Pane root, Stage primaryStage) {
        if (indice < listaEstaciones.size() - 1) {
            String estacionA = listaEstaciones.get(indice);
            String estacionB = listaEstaciones.get(indice + 1);
            
            moverCuadrado(estacionA, estacionB, () -> {
                sumarPesoConexion(estacionA, estacionB);
                avanzaEstacion(indice + 1, root, primaryStage);
            });
        } else {
            // Cuando pasen 5 segundos despues de terminar, 
            // vuelve a la pantalla de eleccion de recorrido
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(5),
                    ae -> mostrarPantallaInicio(root, primaryStage)
            ));
            timeline.play();
        }
    }
    

    public static void sumarPesoConexion(String estacionA, String estacionB) {
        EstacionData dataEstacionA = estaciones.get(estacionA);
        if (dataEstacionA != null) {
            int pesoConexion = dataEstacionA.getPesoHacia(estacionB);
            // System.out.println("Peso de " + estacionA + " a " + estacionB + ": " + pesoConexion);

            int pesoActual = Integer.parseInt(pesoTotal.getText().split(": ")[1]);
            // System.out.println("Peso actual antes de sumar: " + pesoActual);

            pesoTotal.setText("Total weight: " + (pesoActual + pesoConexion));

            // System.out.println("Nuevo peso total: " + (pesoActual + pesoConexion));
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
            transition.setDuration(Duration.seconds(1));
            transition.setCycleCount(1);

            transition.setOnFinished(e -> onFinish.run());

            transition.play();
        }
    }

}
