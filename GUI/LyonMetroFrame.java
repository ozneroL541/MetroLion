package GUI;

import java.awt.Color;

import javax.swing.JFrame;

public class LyonMetroFrame extends JFrame {
    public LyonMetroFrame() {
        super("Lyon Metro");
        this.setSize(800, 450);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
}
