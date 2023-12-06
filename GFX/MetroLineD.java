package GFX;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MetroLineD extends MetroLine {

    private static final int NUM_STATIONS = 15;
    private static final int OFFSET_X = 10;
    private static final int OFFSET_Y = 5;
    private static final int pesos[] = { 1, 2, 3, 2, 1, 2, 1, 3, 1, 2, 1, 2, 2, 2};

    public MetroLineD(Pane root) {
        super("D", Color.GREEN, 0, new String[] {
            "Gare de Vaise", "Valmy", "Gorge de Loup",
            "Vieux Lyon Cathedrale St-Jean",
            "Bellecour", "Guillotiere", "Saxe Gambetta", "Garibaldi",
            "Sans-Souci", "Monplaisir Lumiere", "Grange Blanche", 
            "Laennec", "Mermoz Pinel", "Parilly", "Gare de Venissieux"
        }, root);
    }

    @Override
    protected void drawLine() {
        int[] beginning = { 70, 130 };
        int[] bellecour = { 190, 250 };
        int[] saxeGambetta = { 540, 450 }; 
        int[] ending = { 1080, 715 };

        // Dibuja la línea de Gare de Vaise hasta Bellecour
        Line line = new Line(beginning[0], beginning[1], bellecour[0], bellecour[1]);
        line.setStroke(lineColor);
        line.setStrokeWidth(3);
        root.getChildren().add(line);

        // Dibuja la línea de Bellecour a Saxe Gambetta
        line = new Line(bellecour[0], bellecour[1], saxeGambetta[0], saxeGambetta[1]);
        line.setStroke(lineColor);
        line.setStrokeWidth(3);
        root.getChildren().add(line);

        // Dibuja la linea de Saxe Gambetta hasta Gare de Venissieux
        line = new Line(saxeGambetta[0], saxeGambetta[1], ending[0], ending[1]);
        line.setStroke(lineColor);
        line.setStrokeWidth(3);
        root.getChildren().add(line);

        // Calcula el espaciado entre las estaciones
        double spaceX = (saxeGambetta[0] - bellecour[0]) / (NUM_STATIONS - 13); // -13 porque se dibujan 3 estaciones
        double spaceY = (saxeGambetta[1] - bellecour[1]) / (NUM_STATIONS - 13);

        double spaceX1 = (ending[0] - saxeGambetta[0]) / (NUM_STATIONS - 7); // -7 se dibujan 7 estaciones
        double spaceY1 = (ending[1] - saxeGambetta[1]) / (NUM_STATIONS - 7);

        for (int i = 0; i < NUM_STATIONS; i++) {
            double stationX, stationY;
            if (i < 4) { // Para las primeras cuatro estaciones
                stationX = beginning[0] + (bellecour[0] - beginning[0]) * i / 4; // 4 intervalos entre las primeras 4 estaciones
                stationY = beginning[1] + (bellecour[1] - beginning[1]) * i / 4;
            } else if (i > 5) { // Para las estaciones despues de Saxe Gambetta
                stationX = saxeGambetta[0] + spaceX1 * (i - 6); 
                stationY = saxeGambetta[1] + spaceY1 * (i - 6);
            } else { // Para estaciones entre Bellecour y Saxe Gambetta
                stationX = bellecour[0] + spaceX * (i - 4); 
                stationY = bellecour[1] + spaceY * (i - 4);
            }
            
            double x = stationX;
            double y = stationY;

            // Anado la posicion de las estaciones y las estaciones a las que conecta
            // Las estaciones que ya existen hay que anadir mas informacion
            EstacionData data = new EstacionData(x, y);
            if (stations[i].equals("Saxe Gambetta") || stations[i].equals("Bellecour"))
                data = MetroMap.getEstacion(stations[i]);
            
            if (i < stations.length - 1) {
                data.addConexion(stations[i + 1], pesos[i]);
                System.out.println(stations[i]);
            }
            if (i > 0)
                data.addConexion(stations[i - 1], pesos[i - 1]);

            MetroMap.addEstacion(stations[i], data);

            // Añadir nombres de estaciones, excepto para la 4 y la 6
            Circle stationCircle = new Circle(stationX, stationY, 5, (i == 4 || i == 6)? Color.BLACK : lineColor);
            root.getChildren().add(stationCircle);

            if (i != 4 && i != 6) {
                Text stationName = new Text(stationX + OFFSET_X, stationY + OFFSET_Y, stations[i]);
                root.getChildren().add(stationName);
            }
        }
    }
}
