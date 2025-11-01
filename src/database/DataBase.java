package database;

import graph.Graph;
import help.HelpMethod;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import task.NewPanel;
import task.Task;

public class DataBase {

  private Task task;
  private Graph graph;
  private HelpMethod hm;
  private File taskFile;
  private File yearFile = null;
  private File previousYearFile = null;
  private int previousYear;
  private int redSum, greenSum;
  private String fileName;
  private String folderPath;
  private String fullPath;
  private String yearFilePath;
  public List<Integer> RedCirclePosition, GreenCirclePosition;
  public List<List<Integer>> CompleteTaskMatrix;
  private static boolean onlyOnce = true;

  public DataBase(Task task, Graph graph, HelpMethod hm) {
    this.task = task;
    this.graph = graph;
    this.hm = hm;

    redSum = greenSum = 0;
    RedCirclePosition = new ArrayList<>();
    GreenCirclePosition = new ArrayList<>();
    CompleteTaskMatrix = new ArrayList<>();

    fileName = hm.getDay() + "_" + hm.getMonth() + "_" + hm.getYear() + ".txt";
    folderPath = "src/textFiles/tasks/";
    fullPath = folderPath + fileName;
    taskFile = new File(fullPath);

    yearFilePath = "src/textFiles/years/";
    yearFile = new File(yearFilePath + hm.getYear() + ".txt");

    previousYearFile = new File("src/textFiles/previousYear/previousYear.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(previousYearFile))) {
      previousYear = Integer.parseInt(reader.readLine());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void FileMaker() {
    try {
      if (!taskFile.createNewFile()) ReadTasks();
      if (yearFile.length() == 0 || hm.getYear() != previousYear) {
        ResetPositions();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(previousYearFile))) {
          writer.write("");
          writer.write(hm.getYear());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void ReadTasks() {
    try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        NewPanel np = new NewPanel(task);
        String description = "";
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) != '|') description += line.charAt(i);
          else {
            np.box.setSelected(line.charAt(i + 1) == 't' ? true : false);
            break;
          }
        }
        np.field.setText(description);
        np.attachListeners();
        task.taskList.add(np);
      }
      task.refreshTasks();
      task.callMultipleMethodsWithoutCallDataBase();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void ResetPositions() {
    if (hm.getYear() != previousYear) {
      yearFile = new File(yearFilePath + hm.getYear() + ".txt");
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(yearFile))) {
      writer.write("");
      for (int i = 0; i < 12; i++) {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
          // System.out.println("max Day : " + hm.getTotalDays(i + 1));
          for (int k = 0; k < hm.getTotalDays(i + 1); k++) {
            writer.write("0 ");
            if (j == 0) {
              list.add(0);
            }
          }
          writer.newLine();
        }
        CompleteTaskMatrix.add(list);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updateTaskFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile))) {
      List<NewPanel> obj = task.taskList;
      for (int i = 0; i < obj.size(); i++) {
        writer.write(
            obj.get(i).field.getText()
                + "|"
                + String.valueOf(obj.get(i).box.isSelected() ? "true" : "false"));
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updatePositionFile() {
    try {
      List<String> lines = getPositionFile();

      String redLine = lines.get((hm.getMonth() * 2) - 2); // 0 base index
      String greenLine = lines.get((hm.getMonth() * 2) - 1);

      RedCirclePosition.clear();
      GreenCirclePosition.clear();

      RedCirclePosition = ConvertStringToList(redLine);
      GreenCirclePosition = ConvertStringToList(greenLine);

      updateDay();

      redLine = ConvertListToString(RedCirclePosition);
      greenLine = ConvertListToString(GreenCirclePosition);

      graph.setMaxTask(Collections.max(RedCirclePosition) + 2);

      lines.set((hm.getMonth() * 2) - 2, redLine);
      lines.set((hm.getMonth() * 2) - 1, greenLine);

      if (onlyOnce) updateCompleteTaskMatrix(lines);

      Files.write(yearFile.toPath(), lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void updateDay() { // update the current day Task.
    RedCirclePosition.set(hm.getDay() - 1, graph.getTotalTask());
    GreenCirclePosition.set(hm.getDay() - 1, graph.getCompleteTask());
  }

  private void updateCompleteTaskMatrix(List<String> lines) {
    CompleteTaskMatrix.clear();
    for (int i = 0; i < 12; i++) {
      CompleteTaskMatrix.add(ConvertStringToList(lines.get(i * 2 + 1)));
    }
    onlyOnce = false;
  }

  public void updateRowOfMatrix() {
    CompleteTaskMatrix.get(hm.getMonth() - 1)
        .set(hm.getDay() - 1, getGreenYPosition(hm.getDay() - 1));
  }

  public void positionFileSum() {
    try (BufferedReader reader = new BufferedReader(new FileReader(yearFile))) {
      String line;
      redSum = greenSum = 0;
      int i = 0;
      while ((line = reader.readLine()) != null) {
        String number = "";
        int sum = 0;
        for (int j = 0; j < line.length(); j++) {
          if (line.charAt(j) != ' ') number += line.charAt(j);
          else {
            sum += Integer.parseInt(number);
            number = "";
          }
        }
        if (!number.isEmpty()) sum += Integer.parseInt(number);
        if (i % 2 == 0) {
          redSum += sum;
        } else {
          greenSum += sum;
        }
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List ConvertStringToList(String line) {
    List<Integer> list = new ArrayList<>();
    for (String number : line.trim().split("\\s+")) {
      list.add(Integer.parseInt(number));
    }
    return list;
  }

  public String ConvertListToString(List<Integer> list) {
    String str = "";
    for (int value : list) str += value + " ";
    return str;
  }

  private List<String> getPositionFile() throws IOException {
    return Files.readAllLines(yearFile.toPath());
  }

  // This is for all month days OK.
  // Getter
  public int getCompleteTaskMatrix(int row, int column) {
    return CompleteTaskMatrix.get(row).get(column);
  }

  public int getRedYPosition(int index) {
    return RedCirclePosition.get(index);
  }

  public int getGreenYPosition(int index) {
    return GreenCirclePosition.get(index);
  }

  public int getRedSum() {
    return redSum;
  }

  public int getGreenSum() {
    return greenSum;
  }
}
