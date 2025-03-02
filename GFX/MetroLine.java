package GFX;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class MetroLine {
    protected String lineName;
    protected Color lineColor;
    protected int lineY;
    protected String[] stations;
    protected Pane root;

    public MetroLine(String lineName, Color lineColor, int lineY, String[] stations, Pane root) {
        this.lineName = lineName;
        this.lineColor = lineColor;
        this.lineY = lineY;
        this.stations = stations;
        this.root = root;
        drawLine();
    }

    protected abstract void drawLine();
}