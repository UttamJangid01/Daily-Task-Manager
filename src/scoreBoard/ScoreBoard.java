package scoreBoard;

import custom.CustomButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import main.MainPanel;

public class ScoreBoard extends JPanel {

  MainPanel mp;
  private Setting s = null;
  private double score;
  private double perTask;
  private CustomButton setting;
  DecimalFormat df;

  public ScoreBoard(MainPanel mp, int width, int height) {
    this.setLayout(null);
    score = 0;
    this.mp = mp;

    df = new DecimalFormat("0.00");
    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(new java.awt.Color(50, 50, 50));

    setting = new CustomButton();
    setting.setDimensions(30, 30, 25, 25);
    setting.setImageMethod(30, 30, "res/images/setting.png");

    setting.setBounds(width - 35, 5, 30, 30);
    this.add(setting);
  }

  public void attachListeners() {
    setting.addActionListener(
        e -> {
          if (s == null) {
            s = new Setting();
            // System.out.println("in");
          }
          if (!s.isVisible()) {
            s.setVisible(true);
          }
        });
  }

  public void update() {
    mp.donutChart.update();
    score = 0;
    // System.out.println(
    // "value : " + mp.donutChart.getFirst() + ", third : " +
    // mp.donutChart.getThird());
    perTask = (double) 10 / mp.donutChart.getFirst();
    score = mp.donutChart.getThird() * perTask;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(Color.white);
    g2.setFont(new Font("", Font.PLAIN, 20));
    g2.drawString(
        "Score : " + df.format(score),
        15,
        (this.getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent());
  }
}
