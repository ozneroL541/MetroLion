package GFX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

        new MetroLineA(root);
        new MetroLineB(root);
        new MetroLineC(root);
        new MetroLineD(root);
        

        pesoTotal = new Label("Peso total: 0");
        pesoTotal.setLayoutX(1280 - 150);
        pesoTotal.setLayoutY(40);
        root.getChildren().add(pesoTotal);

        cuadradoAmarillo = new Rectangle(12, 12, Color.YELLOW);
        cuadradoAmarillo.setX(estaciones.values().iterator().next().getX());
        cuadradoAmarillo.setY(estaciones.values().iterator().next().getY());
        root.getChildren().add(cuadradoAmarillo);


        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setTitle("Mapa del Metro");
        primaryStage.setScene(scene);
        primaryStage.show();

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

        // Cuando se tenga la lista del mejor camino A* hay que insertarla en listaEstaciones
        listaEstaciones.add("Perrache");
        listaEstaciones.add("Ampere Victor Hugo");
        listaEstaciones.add("Bellecour");
        listaEstaciones.add("Guillotiere");
        listaEstaciones.add("Saxe Gambetta");
        listaEstaciones.add("Place Guichard Bourse du Travail");
        listaEstaciones.add("Gare Part-Dieu Vivier Merle");
        listaEstaciones.add("Brotteaux");
        listaEstaciones.add("Charpennes Charles Hernu");

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
