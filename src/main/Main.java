package main;

import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

public class Main {
  public static void main(String args[]) {
    JFrame window = new JFrame();
    window.setUndecorated(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setShape(new RoundRectangle2D.Double(0, 0, 1500, 800, 25, 25));
    window.setSize(1500, 800);
    window.setLocationRelativeTo(null);
    window.setResizable(false);

    MainPanel mainPanel = new MainPanel();

    window.add(mainPanel);
    window.pack();
    window.setVisible(true);
  }
}
