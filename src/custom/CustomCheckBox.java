package custom;

import java.awt.*;
import javax.swing.*;

public class CustomCheckBox extends JCheckBox {

    private int customWidth = 35;
    private int customHeight = 35;

    public CustomCheckBox(String text, int width, int height) {
        super(text);
        customWidth = width;
        customHeight = height; 
        setOpaque(false); // background transparent
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        // Optional: Hand cursor on hover
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Remove default size layout issues
        setPreferredSize(
                new Dimension(
                        customWidth + getFontMetrics(getFont()).stringWidth(text) + 10, customHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 2D rendering for anti-aliasing
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Positioning
        int x = 0;
        int y = (getHeight() - customHeight) / 2;

        // Rounded rectangle shape
        int arc = (int) (customHeight * 0.5); // 50% of height = 25% border radius

        // Colors
        Color borderColor = isSelected() ? new Color(85, 255, 100) : Color.GRAY;
        Color fillColor = isSelected() ? new Color(50, 50, 50) : Color.WHITE;

        // Draw checkbox border
        g2.setColor(fillColor);
        g2.fillRoundRect(x, y, customWidth, customHeight, arc, arc);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, customWidth, customHeight, arc, arc);

        // Draw tick
        if (isSelected()) {
            this.setIcon(
                    new ImageIcon(
                            new ImageIcon("res/images/checkmark.png")
                                    .getImage()
                                    .getScaledInstance(customWidth - 5, customHeight - 5, Image.SCALE_SMOOTH)));
        } else {
            this.setIcon(null);
        }

        g2.dispose();
    }
}
