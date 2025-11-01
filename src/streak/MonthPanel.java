package streak;

import custom.CustomBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.MainPanel;

public class MonthPanel extends JPanel {

  private MainPanel mp;
  private final int width = 93, height = 130;
  private final int boxWidth = 14, boxHeight = 14;
  private static int count = 1;
  private JPanel panel, one, two, three, four, five, six;
  private JLabel label;
  private Color color;
  private List<String> monthNames =
      List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
  private int monthNumber;
  private List<CustomBox> customBoxsList;

  public MonthPanel(MainPanel mp, int monthNumber) {
    this.mp = mp;
    this.monthNumber = monthNumber;
    customBoxsList = new ArrayList<>();

    one = new JPanel();
    two = new JPanel();
    three = new JPanel();
    four = new JPanel();
    five = new JPanel();
    six = new JPanel();
    label = new JLabel(monthNames.get(monthNumber));
    color = new Color(50, 50, 50);

    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);

    this.setLayout(new BorderLayout(0, 0));
    panel = new JPanel(new GridLayout(1, 6, 2, 0));
    one.setLayout(new GridLayout(7, 1));
    two.setLayout(new GridLayout(7, 1));
    three.setLayout(new GridLayout(7, 1));
    four.setLayout(new GridLayout(7, 1));
    five.setLayout(new GridLayout(7, 1));
    six.setLayout(new GridLayout(7, 1));

    label.setForeground(Color.white);
    panel.setBackground(color);
    one.setBackground(color);
    two.setBackground(color);
    three.setBackground(color);
    four.setBackground(color);
    five.setBackground(color);
    six.setBackground(color);

    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(color);

    LocalDate date = LocalDate.of(mp.helpMethod.getYear(), monthNumber + 1, 1);
    int length = date.lengthOfMonth();
    int offset = date.getDayOfWeek().getValue() % 7;

    // System.out.println("length : " + length);
    // System.out.println("offset : " + offset);

    int day = 1;
    for (int week = 0; week < 6; week++) {
      JPanel targetPanel =
          switch (week) {
            case 0 -> one;
            case 1 -> two;
            case 2 -> three;
            case 3 -> four;
            case 4 -> five;
            case 5 -> six;
            default -> null;
          };

      for (int i = 0; i < 7; i++) {
        if (week == 0 && i < offset)
          targetPanel.add(new CustomBox("", boxWidth, boxHeight, 0, 0, false));
        else if (day <= length) {
          // System.out.println("Month number : " + monthNumber + ", day : " + (day - 1));
          CustomBox customBox =
              new CustomBox(
                  "",
                  boxWidth,
                  boxHeight,
                  255,
                  mp.data.getCompleteTaskMatrix(monthNumber, day - 1),
                  true);
          targetPanel.add(customBox);
          customBoxsList.add(customBox);
          day++;
        } else targetPanel.add(new CustomBox("", boxWidth, boxHeight, 0, 0, false));
      }
    }

    panel.add(one);
    panel.add(two);
    panel.add(three);
    panel.add(four);
    panel.add(five);
    panel.add(six);

    this.add(panel, BorderLayout.CENTER);
    this.add(label, BorderLayout.SOUTH);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  // Getters
  public List getList() {
    // System.out.println("List is Returned.");
    return customBoxsList;
  }
}
