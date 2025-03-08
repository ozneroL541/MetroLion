package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIManager extends JFrame {
    private JComboBox<String> initialStationComboBox;
    private JComboBox<String> destinationStationComboBox;
    private JButton calculatePathButton;
    private JTextArea resultTextArea;

    public GUIManager() {
        setTitle("Metro Lyon");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        initialStationComboBox = new JComboBox<>(getStations());
        destinationStationComboBox = new JComboBox<>(getStations());
        calculatePathButton = new JButton("Calculate Path");
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        add(new JLabel("Select Initial Station:"));
        add(initialStationComboBox);
        add(new JLabel("Select Destination Station:"));
        add(destinationStationComboBox);
        add(calculatePathButton);
        add(new JScrollPane(resultTextArea));

        calculatePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initialStation = (String) initialStationComboBox.getSelectedItem();
                String destinationStation = (String) destinationStationComboBox.getSelectedItem();
                String bestPath = calculateBestPath(initialStation, destinationStation);
                resultTextArea.setText(bestPath);
            }
        });
    }

    private String[] getStations() {
        // This should return the list of stations
        return new String[]{"Station1", "Station2", "Station3", "Station4"};
    }

    private String calculateBestPath(String initialStation, String destinationStation) {
        // This should implement the A* algorithm to find the best path
        // For now, we return a placeholder string
        return "Best path from " + initialStation + " to " + destinationStation;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIManager().setVisible(true);
            }
        });
    }
}
