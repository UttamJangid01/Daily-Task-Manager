package task;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import main.MainPanel;
import custom.CustomButton;

public class Task extends JPanel {

    public JPanel panel, leftPanel, rightPanel;
    private MainPanel mp;
    public static int completeTask = 0;
    boolean OneTime = true;
    CustomButton addButton;
    CustomButton submitButton;
    CustomButton cleanAll;
    public List<NewPanel> taskList;

    public Task(MainPanel mp, int width, int height) {
        this.mp = mp;
        panel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        this.setLayout(null);
        panel.setLayout(new GridLayout(1, 2));
        leftPanel.setLayout(new GridLayout(7, 1));
        rightPanel.setLayout(new GridLayout(7, 1));

        taskList = new ArrayList<>();
        this.setPreferredSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width - 55, height-5));

        this.setBackground(new java.awt.Color(50, 50, 50));
        panel.setBackground(new java.awt.Color(50, 50, 50));
        leftPanel.setBackground(new java.awt.Color(50, 50, 50));
        rightPanel.setBackground(new java.awt.Color(50, 50, 50));

        panel.setBounds(15, 5, width - 70, height-5);      // x = 0, width-55

        addButton = new CustomButton();
        submitButton = new CustomButton();
        cleanAll = new CustomButton();

        addButton.setDimensions(50, 50, 25, 25);
        addButton.setImageMethod("res/images/add.png");

        submitButton.setDimensions(50, 50, 25, 25);
        submitButton.setImageMethod("res/images/submit.png");

        cleanAll.setDimensions(50, 50, 25, 25);
        cleanAll.setImageMethod("res/images/cleanAll.png");

        addButton.setBounds(width - 55, 5, addButton.width, addButton.height);
        submitButton.setBounds(width - 55, 65, submitButton.width, submitButton.height);
        cleanAll.setBounds(width-55, 125, cleanAll.width, cleanAll.height);

        this.add(addButton);
        this.add(submitButton);
        this.add(cleanAll);

        panel.add(leftPanel);
        panel.add(rightPanel);
        this.add(panel);
    }

    public void attachListeners() {
        addButton.addActionListener(e -> {
            addTask();
        });

        submitButton.addActionListener(e -> {
            refreshTasks();
            callMultipleMethods();
        });

        cleanAll.addActionListener(e ->{
            taskList.clear();
            refreshTasks();
            callMultipleMethods();
        });
    }

    public void callMultipleMethods() {
        mp.graph.setTotalTask(taskList.size());
        mp.graph.setCompleteTask(completeTask);
        
        
        mp.data.updatePositionFile();   // the Red, Green YPositinos are updated this time.
        mp.data.updateTaskFile();
        mp.streak.update(); 
        mp.scoreBoard.update();
        mp.streak.addAllMonthsOnPanel();

        mp.scoreBoard.repaint();
        mp.donutChart.repaint();

        mp.graph.upDate();
    }

    public void callMultipleMethodsWithoutCallDataBase() {  // when current day file is already created
        mp.graph.setTotalTask(taskList.size());
        mp.graph.setCompleteTask(completeTask);
        
        mp.scoreBoard.update();

        mp.scoreBoard.repaint();
        mp.donutChart.repaint();

        mp.graph.upDate();
    }

    private void addTask() {
        if (taskList.size() < 14) {
            NewPanel np = new NewPanel(this);
            np.attachListeners();
            taskList.add(np);
        } else
            JOptionPane.showMessageDialog(null, "Maximum panels reached!");

        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).number.setText(String.valueOf(i + 1));
            if (i < 7)
                leftPanel.add(taskList.get(i));
            else
                rightPanel.add(taskList.get(i));

            panel.add(leftPanel);
            panel.add(rightPanel);
        }
        panel.revalidate();
        panel.repaint();
        this.add(panel);
    }

    public void refreshTasks() {
        leftPanel.removeAll();
        rightPanel.removeAll();
        panel.removeAll();
        for (int i = taskList.size() - 1; i >= 0; i--) {
            if (taskList.get(i).field.getText().isEmpty()) {
                taskList.remove(i);
            }
        }
        completeTask = 0;
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).number.setText(String.valueOf(i + 1));
            if (taskList.get(i).box.isSelected())
                completeTask++;
            if (i < 7)
                leftPanel.add(taskList.get(i));
            else
                rightPanel.add(taskList.get(i));
        }

        panel.add(leftPanel);
        panel.add(rightPanel);
        this.add(panel);
        revalidate();
        repaint();
    }

    public void setData() {
        // get the data from Database file and add it to the window.

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
