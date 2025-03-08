package GUI;

import javax.swing.*;
import java.awt.*;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public class MetroMap {
    public static final String[] STATIONS = {
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
    };

    private static final Path mapName = Path.of("images", "Plan-Lyon-Metro-Tramway.svg");

    public MetroMap() {
        JFrame frame = new JFrame("Lyon Metro Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ImageIcon metroMapImage = new ImageIcon(mapName.toAbsolutePath().toString());
        JLabel imageLabel = new JLabel(metroMapImage);
        frame.add(imageLabel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MetroMap());
    }
}
