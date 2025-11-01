package main;

import database.DataBase;
import graph.Graph;
import graph.DonutChart;
import help.HelpMethod;
import scoreBoard.ScoreBoard;

import java.awt.Dimension;

import javax.swing.JPanel;
import streak.Streak;
import task.Task;

public class MainPanel extends JPanel {
    private final int width, height;

    // Objects
    public DataBase data;
    public Graph graph;
    public ScoreBoard scoreBoard;
    public DonutChart donutChart;
    public Streak streak;
    public Task task;
    public HelpMethod helpMethod;

    /// --------------------
    
    public MainPanel() {
        width = 1500;
        height = 800;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new java.awt.Color(50, 50, 50));
        
        helpMethod = new HelpMethod();
        
        graph = new Graph(this, (width * 3) / 4, 450);
        graph.setBounds(0, 0, (width * 3) / 4, 451);

        scoreBoard = new ScoreBoard(this, width-(width*3)/4, 40);
        scoreBoard.setBounds((width * 3) / 4, 0, width - (width * 3)/ 4, 40);
        
        donutChart = new DonutChart(this, width - (width * 3) / 4, 370);
        donutChart.setBounds((width * 3) / 4, 40, width - (width * 3)/ 4, 370);
        
        task = new Task(this, (width * 3) / 4, 350);
        task.setBounds(0, 450, (width * 3) / 4, 350);
        
        streak = new Streak(this, width - (width * 3) / 4, 390);
        streak.setBounds((width * 3) / 4, 410, width - (width * 3) / 4, 390);
        
        data = new DataBase(task, graph, helpMethod);

        callRequiredMethods();
        
        this.add(graph);
        this.add(scoreBoard);
        this.add(donutChart);
        this.add(task);
        this.add(streak);
    }

    private void callRequiredMethods(){
        donutChart.attachListeners();
        scoreBoard.attachListeners();
        task.attachListeners();
        data.FileMaker();
        data.updatePositionFile();
        graph.setTaskPosition();
        graph.setThings();
        donutChart.update();
        streak.addAllMonthsOnPanel();
    }

    // public void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     Graphics2D g2 = (Graphics2D) g;
    // }
}
