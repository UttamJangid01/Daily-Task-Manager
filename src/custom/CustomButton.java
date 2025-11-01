package custom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class CustomButton extends JButton {
    public int width, height, arcWidth, arcHeight;

    public CustomButton() {
        super();
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }

    public void setDimensions(int width, int height, int arcWidth, int arcHeight) {
        this.width = width;
        this.height = height;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    public void setImageMethod(String path) {
        this.setIcon(new ImageIcon(
                new ImageIcon(path)
                        .getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }

    public void setImageMethod(int width, int height, String path) {
        this.setIcon(new ImageIcon(
                new ImageIcon(path)
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_SMOOTH)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);
        super.paintComponent(g2);
        g2.dispose();
    }
}
