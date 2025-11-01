package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import main.MainPanel;
import triple.Triple;

public class DonutChart extends JPanel {

  MainPanel mp;
  private int startAngle;
  private int completeSweep;
  private int doSweep;
  private int remainSweep;
  private double anglePerTask;
  private double completedAngle;
  private double doAngle;
  private double remainingAngle;
  private String[] list = {"Day", "Month", "Year"};
  private JComboBox<String> box;
  private Triple<Integer, Integer, Integer> triple;

  public DonutChart(MainPanel mp, int width, int height) {
    this.mp = mp;
    this.setLayout(null);
    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(new java.awt.Color(50, 50, 50));
    triple = new Triple<>();
    box = new JComboBox<>(list);
    box.setSelectedIndex(0);
    box.setBounds(width - 70, 10, 65, 20);
    this.add(box);
  }

  public void attachListeners() {
    box.addActionListener(
        e -> {
          mp.scoreBoard
              .update(); // I am call DonutChart update in the scoreBoard class update method.
          // mp.scoreBoard.revalidate();
          mp.scoreBoard.repaint();
          repaint();
        });
  }

  public void update() {
    getTotal();
    // System.out.println("Dvalue : " + getFirst() + ", third : " + getThird());

    startAngle = 90;
    anglePerTask = 360.0 / triple.first;

    doAngle = triple.second * anglePerTask;
    completedAngle = triple.third * anglePerTask;
    remainingAngle = 360.0 - doAngle;

    // Use Math.round() and calculate second startAngle using the first
    completeSweep = (int) Math.round(completedAngle);
    doSweep = (int) Math.round(doAngle);
    remainSweep = 360 - doSweep;
  }

  private void getTotal() {
    if (box.getSelectedItem() == "Day") {
      triple.setFirst(14);
      triple.setSecond(mp.graph.getTotalTask());
      triple.setThird(mp.graph.getCompleteTask());
    } else if (box.getSelectedItem() == "Month") {
      int redSum = 0, greenSum = 0;
      for (int i = 0; i < mp.data.RedCirclePosition.size(); i++) {
        redSum += mp.data.RedCirclePosition.get(i);
        greenSum += mp.data.GreenCirclePosition.get(i);
      }
      triple.setFirst(14 * mp.helpMethod.getTotalDays(mp.helpMethod.getMonth()));
      triple.setSecond(redSum);
      triple.setThird(greenSum);
    } else if (box.getSelectedItem() == "Year") {
      mp.data.positionFileSum();

      triple.setFirst(14 * (mp.helpMethod.checkYearIsLeep() ? 366 : 365));
      triple.setSecond(mp.data.getRedSum());
      triple.setThird(mp.data.getGreenSum());
    }
  }

  private void drawDonutChart(Graphics2D g2) {
    g2.setColor(Color.green);
    g2.fillArc(37, 37, 300, 300, startAngle, -completeSweep);

    g2.setColor(Color.red);
    g2.fillArc(37, 37, 300, 300, startAngle - completeSweep, -(doSweep - completeSweep));

    g2.setColor(Color.gray);
    // g2.setColor(new java.awt.Color(50, 50, 50));
    g2.fillArc(37, 37, 300, 300, startAngle - doSweep, -remainSweep);

    g2.setColor(new java.awt.Color(50, 50, 50));
    // g2.fillArc(72, 72, 230, 230, 0, 360); // width 70
    g2.fillArc(77, 77, 220, 220, 90, 360); // width 80
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    drawDonutChart(g2);
  }

  // Getters
  public int getFirst() {
    return triple.getFirst();
  }

  public int getSecond() {
    return triple.getSecond();
  }

  public int getThird() {
    return triple.getThird();
  }
}
