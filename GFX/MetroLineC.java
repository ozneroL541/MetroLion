package GFX;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MetroLineC extends MetroLine {

    private static final String[] stations = {
        "Cuire", "Hénon", "Croix-Rousse", "Croix-Paquet", "Hôtel de Ville - Louis Pradel",
    };

    private static final int STATION_SPACING = 50;
    private static final int START_Y = 50;
    private static final int LINE_X = 330;
    private static final int OFFSET_X = 12;
    private static final int OFFSET_Y = 4;
    private static final int pesos[] = { 2, 3, 2, 2};

    public MetroLineC(Pane root) {
        super("B", Color.ORANGE, LINE_X, stations, root);
    }

    @Override
    protected void drawLine() {
        for (int i = 0; i < stations.length; i++) {
            // Dibujar líneas verticales entre estaciones
            if (i < stations.length - 1) {
                Line line = new Line(LINE_X, START_Y + i * STATION_SPACING, LINE_X, START_Y + (i + 1) * STATION_SPACING);
                line.setStroke(lineColor);
                line.setStrokeWidth(3);
                root.getChildren().add(line);
            }

            // Dibujar círculos para representar las estaciones
            double x = LINE_X;
            double y = START_Y + i * STATION_SPACING;

            // Anado la posicion de las estaciones y las estaciones a las que conecta
            // Las estaciones que ya existen hay que anadir mas informacion
            EstacionData data = new EstacionData(x, y);
            if (stations[i].equals("Hôtel de Ville - Louis Pradel"))
                data = MetroMap.getEstacion(stations[i]);

            if (i < stations.length - 1)
                data.addConexion(stations[i + 1], pesos[i]);

            if (i > 0)
                data.addConexion(stations[i - 1], pesos[i - 1]);
            
            MetroMap.addEstacion(stations[i], data);

            Circle stationCircle = new Circle(LINE_X, START_Y + i * STATION_SPACING, 5, (i == 4)? Color.BLACK : lineColor);
            root.getChildren().add(stationCircle);

            // Añadir nombres de estaciones, excepto la ultima
            if (i != stations.length -1) {
                String[] stationLines = stations[i].split("\n");
                for (int j = 0; j < stationLines.length; j++) {
                    Text stationName = new Text(LINE_X + OFFSET_X, START_Y + i * STATION_SPACING + OFFSET_Y, stationLines[j]);
                    root.getChildren().add(stationName);
                }
            }
        }
    }
}
