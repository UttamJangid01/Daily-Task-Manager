package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import help.Circle;
import help.Points;
import main.MainPanel;

public class Graph extends JPanel {

    MainPanel mp;
    private final int width, height;
    private final float cwidth, cheight;
    private int MaxTask = 5;
    private int totalTask, completeTask;
    private boolean OneTime;
    private ArrayList<Integer> taskPosition;
    public Map<Integer, Circle> days;

    public Graph(MainPanel mp, int width, int height) {
        this.mp = mp;
        this.width = width;
        this.height = height;
        cwidth = cheight = 5;
        OneTime = true;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new java.awt.Color(50, 50, 50));
        taskPosition = new ArrayList<>();
        days = new HashMap<>();

        setTaskPosition();
        // setThings();
    }

    public void setTaskPosition() {
        taskPosition.clear();
        MaxTask = Math.max(MaxTask, totalTask + 2);
        MaxTask = Math.max(MaxTask, 5);
        float gap = (float) 400 / MaxTask;
        float sum = 420;
        for (int i = 0; i <= MaxTask; i++) {
            taskPosition.add((int) sum);
            sum -= gap;
        }
    }

    public void setThings() {
        int totalDays = mp.helpMethod.getTotalDays(mp.helpMethod.getMonth());
        float gap = (float) (width - 58) / totalDays;
        float sum = 60;
        for (int i = 1; i <= totalDays; i++) {
            days.put(i, new Circle(new Points(sum, taskPosition.get(mp.data.getRedYPosition(i - 1))),
                    new Points(sum, taskPosition.get(mp.data.getGreenYPosition(i - 1))))); // red, green
            sum += gap;
        }
    }

    private void drawTask(Graphics2D g2) { // Task 1 to 10/14(16)
        for (int i = 0; i < taskPosition.size(); i++) {
            g2.setColor(new java.awt.Color(80, 80, 80));
            g2.drawLine(60, taskPosition.get(i), width - 30, taskPosition.get(i));

            g2.setColor(Color.white);
            if (totalTask != 0 && taskPosition.get(i) == taskPosition.get(totalTask)) {
                g2.setColor(Color.red);
            }
            if (totalTask != 0 && taskPosition.get(i) == taskPosition.get(completeTask)) {
                g2.setColor(Color.green);
            }
            g2.drawString("" + (i), 35, taskPosition.get(i) + 4);
            // g2.drawLine((int) (st - 2.5), taskPosition.get(i), (int) (st + 2.5),
            // taskPosition.get(i));
        }

    }

    private void drawDays(Graphics2D g2) { // Days eg. 1, 2, 3, 4 .. 30.
        g2.setColor(Color.white);
        for (Map.Entry<Integer, Circle> entry : days.entrySet()) {
            int key = entry.getKey();
            Circle c = entry.getValue();
            Points red = c.red;
            g2.drawString("" + key, red.x - 4, 440);
        }
    }

    private void drawCircles(Graphics2D g2) { // Circle
        setThings();
        g2.setColor(Color.white);
        for (Map.Entry<Integer, Circle> entry : days.entrySet()) {
            Circle c = entry.getValue();
            Points red = c.red;
            Points green = c.green;

            g2.fillOval((int) Math.round(red.x - (cwidth / 2)), (int) Math.round(red.y - (cheight / 2)), (int) cwidth,
                    (int) cheight);
            g2.fillOval((int) Math.round(green.x - (cwidth / 2)), (int) Math.round(green.y - (cheight / 2)),
                    (int) cwidth, (int) cheight);
        }
    }

    private void connectLines(Graphics2D g2) { // Lines
        for (int i = 1; i < days.size(); i++) {
            Circle c1 = days.get(i);
            Circle c2 = days.get(i + 1);

            drawLineMethod(c1.green, c2.green, Color.green, g2);
            drawLineMethod(c1.red, c2.red, Color.red, g2);

            if (mp.data.getRedYPosition(i) != 0
                    && mp.data.getRedYPosition(i) == mp.data.getGreenYPosition(i)) {
                drawLineMethod(c1.green, c2.green, Color.green, g2);
            }
        }
    }

    public void drawLineMethod(Points p1, Points p2, Color color, Graphics2D g2) {
        g2.setColor(color);
        g2.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }

    public void upDate() {
        setTaskPosition();
        Circle c = days.get(mp.helpMethod.getDay() - 1);
        if (c != null) {
            c.red.y = taskPosition.get(totalTask);
            c.green.y = taskPosition.get(completeTask);
        }
        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(80, 80, 80));
        g2.drawLine(60, 20, 60, 420);

        drawTask(g2);
        if (OneTime)
            drawDays(g2);
        drawCircles(g2);
        connectLines(g2);
    }

    // Setter and Getter both for current day not for all month OK.
    // Setter
    public void setMaxTask(int MaxTask) {
        this.MaxTask = MaxTask;
    }

    public void setTotalTask(int totalTask) {
        this.totalTask = totalTask;
    }

    public void setCompleteTask(int value) {
        completeTask = value;
    }

    // Getter
    public int getMaxTask() {
        return MaxTask;
    }

    public int getTotalTask() {
        return totalTask;
    }

    public int getCompleteTask() {
        return completeTask;
    }
}
