package task;

import custom.CustomButton;
import custom.CustomCheckBox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPanel extends JPanel {
  private final int width, height;
  public JLabel number;
  public JTextField field;
  public CustomCheckBox box;
  CustomButton edit;
  CustomButton delete;
  Task task;

  public NewPanel(Task task) {
    this.task = task;
    width = 515;
    height = 40;
    this.setLayout(null);
    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(new java.awt.Color(50, 50, 50));

    number = new JLabel();
    field = new JTextField();
    box = new CustomCheckBox("", 35, 35);
    edit = new CustomButton();
    delete = new CustomButton();

    setDimension();
    setImages();
    setDifferentThings();
    setFontMethod();
    setColorMethod();
    setMethod();

    this.add(number);
    this.add(field);
    this.add(box);
    this.add(edit);
    this.add(delete);
  }

  public void attachListeners() {
    box.addItemListener(
        e -> {
          if (e.getStateChange() == ItemEvent.SELECTED) {
            // System.out.println("yes");
          } else {
            // System.out.println("no");
          }
          task.refreshTasks();
          task.callMultipleMethods();
        });

    edit.addActionListener(
        e -> {
          String newText =
              JOptionPane.showInputDialog(
                  NewPanel.this, "Enter new text", "Edit Button", JOptionPane.PLAIN_MESSAGE);

          // null = cancel, "" = empty string
          if (newText != null && !newText.trim().isEmpty()) {
            edit.setText(newText.trim());
            field.setText(newText);
            task.callMultipleMethods();
          }
        });

    delete.addActionListener(
        e -> {
          this.field.setText("");
          task.refreshTasks();
          task.callMultipleMethods();
        });
  }

  private void setDimension() {
    // field.setDimensions(320, 40, 25, 25);
    edit.setDimensions(40, 40, 25, 25);
    delete.setDimensions(40, 40, 25, 25);
  }

  private void setImages() {
    edit.setIcon(
        new ImageIcon(
            new ImageIcon("res/images/edit.png")
                .getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

    delete.setIcon(
        new ImageIcon(
            new ImageIcon("res/images/delete.png")
                .getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
  }

  private void setDifferentThings() {
    edit.setBorderPainted(false); // Border paint disable
    edit.setFocusPainted(false); // Focus outline disable
    edit.setContentAreaFilled(false); // Background fill disable (for custom UI)

    delete.setBorderPainted(false); // Border paint disable
    delete.setFocusPainted(false); // Focus outline disable
    delete.setContentAreaFilled(false); // Background fill disable (for custom UI)
  }

  private void setFontMethod() {
    field.setFont(new Font("", Font.PLAIN, 25));
    number.setFont(new Font("", Font.PLAIN, 30));
  }

  private void setColorMethod() {
    number.setForeground(Color.white);
  }

  private void setMethod() {
    number.setBounds(0, 0, 45, 40);
    field.setBounds(45, 0, 320, 40);
    box.setBounds(375, 0, 40, 40);
    edit.setBounds(425, 0, edit.width, edit.height);
    delete.setBounds(475, 0, delete.width, delete.height);
  }

  public String getLabelNumber() {
    return number.getText();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
