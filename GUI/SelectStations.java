package GUI;

import javax.swing.*;

import GUI.AuxiliaryClasses.UserChoice;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectStations extends JFrame {
    private JComboBox<String> station1ComboBox = new JComboBox<>(MetroMap.STATIONS);
    private JComboBox<String> station2ComboBox = new JComboBox<>(MetroMap.STATIONS);
    private JButton calculatePathButton = new JButton("Calculate Path");;

    public SelectStations() {
        setTitle("Select Stations");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add components to the frame
        add(new JLabel("Departure Station:"), gbc);
        gbc.gridx = 1;
        add(station1ComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Destination Station:"), gbc);
        gbc.gridx = 1;
        add(station2ComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(calculatePathButton, gbc);

        // Add button action listener
        calculatePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String station1 = (String) station1ComboBox.getSelectedItem();
                String station2 = (String) station2ComboBox.getSelectedItem();
                UserChoice userChoice = new UserChoice(station1, station2);
                System.out.println(userChoice.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectStations().setVisible(true);
            }
        });
    }
}
