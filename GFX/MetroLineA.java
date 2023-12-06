package GFX;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MetroLineA extends MetroLine {

    private static final int STATION_SPACING = 70;
    private static final int LINE_Y = 250;
    private static final int OFFSET_X = -30;
    private static final int OFFSET_Y = 25;
    private static final int LINE_HEIGHT = 15;
    private static final int pesos[] = { 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2};

    public MetroLineA(Pane root) {
        super("A", Color.RED, LINE_Y, new String[]{
            "Perrache", "Ampere\nVictor Hugo", "Bellecour", "Cordeliers",
            "Hotel de\nVille Louis\nPradel", "Foch", "Massena", "Charpennes\nCharles\nHernu",
            "Republique\nVilleurbanne", "Gratte-Ciel", "Flachet", "Cusset",
            "Laurent\nBonnevay\nAstroballe", "Vaulx-en-Velin\nLa Soie"
            
        }, root);
    }

    @Override
    protected void drawLine() {
        // Dibujar línea horizontal entre estaciones
        for (int i = 0; i < stations.length; i++) {
            if (i < stations.length - 1) {
                Line line = new Line(50 + i * STATION_SPACING, lineY, 50 + (i + 1) * STATION_SPACING, lineY);
                line.setStroke(lineColor);
                line.setStrokeWidth(3);
                root.getChildren().add(line);
            }

            // Dibujar círculos para representar las estaciones
            double x = 50 + i * STATION_SPACING;
            double y = lineY;

            // Anado la posicion de las estaciones y las estaciones a las que conecta
            EstacionData data = new EstacionData(x, y);

            if (i < stations.length - 1) 
                data.addConexion(stations[i + 1].replaceAll("\n", " "), pesos[i]);
            if (i > 0) 
                data.addConexion(stations[i - 1].replaceAll("\n", " "), pesos[i - 1]);
            
            MetroMap.addEstacion(stations[i].replaceAll("\n", " "), data);

            Circle stationCircle = new Circle(50 + i * STATION_SPACING, lineY, 5, lineColor);
            root.getChildren().add(stationCircle);

            String[] stationLines = stations[i].split("\n");
            // Anado nombre a las estaciones
            for (int j = 0; j < stationLines.length; j++) {
                Text stationName; 
                if (i == 7)
                    stationName = new Text(65 + i * STATION_SPACING + OFFSET_X, -65 + lineY + OFFSET_Y + j * LINE_HEIGHT, stationLines[j]); 
                else 
                    stationName = new Text(55 + i * STATION_SPACING + OFFSET_X, lineY + OFFSET_Y + j * LINE_HEIGHT, stationLines[j]);
                if (i == 2)
                    System.out.println(55 + i * STATION_SPACING + OFFSET_X);

                root.getChildren().add(stationName);
            }
        }
    }
}
