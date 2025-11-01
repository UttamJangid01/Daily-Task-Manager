package streak;

import custom.CustomBox;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import main.MainPanel;

public class Streak extends JPanel {

  public MainPanel mp;
  private JPanel panel;
  public List<MonthPanel> monthList;
  public Map<Integer, List<CustomBox>> daysListMap;

  public Streak(MainPanel mp, int width, int height) {
    this.setLayout(null);
    this.mp = mp;
    panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    panel.setLayout(new GridLayout(3, 4, 2, 2));

    this.setPreferredSize(new Dimension(width, height));
    panel.setPreferredSize(new Dimension(width, height));

    this.setBackground(new java.awt.Color(50, 50, 50));
    panel.setBackground(new java.awt.Color(50, 50, 50));
    // panel.setBackground(Color.white);

    panel.setBounds(0, 0, width, height);

    monthList = new ArrayList<>();
    daysListMap = new HashMap<>();

    this.add(panel);
  }

  public void addAllMonthsOnPanel() {
    panel.removeAll();
    for (int i = 0; i < 12; i++) {
      MonthPanel monthPanel = new MonthPanel(mp, i);
      monthList.add(monthPanel);
      daysListMap.put(i, monthPanel.getList());
      panel.add(monthList.get(i));
    }
    List<CustomBox> customBoxs = monthList.get(6).getList();
    CustomBox cbox = customBoxs.get(26);
    // System.out.println("Level : "+cbox.getLevel());
  }

  public void update() {
    List<CustomBox> customBoxList = monthList.get(mp.helpMethod.getMonth() - 1).getList();
    CustomBox customBox = customBoxList.get(mp.helpMethod.getDay() - 1);
    customBox.setLevel(mp.graph.getCompleteTask());
    customBox.repaint();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
  }
}
