package custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CustomBox extends JLabel {
    private final int width, height;
    private int transparency;
    public int level;
    private boolean toolTip;

    public CustomBox(String text, int width, int height, int transparency, int level, boolean toolTip) {
        super(text);
        this.level = level;
        this.width = width;
        this.height = height;
        this.transparency = transparency;
        this.toolTip = toolTip;
        setOpaque(false); // Let us paint background ourselves
        setForeground(Color.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 7));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        if(toolTip)
            setToolTipText("Level : "+level);
        // 25% rounded corners
        int arcWidth = (int) (width * 0.25);
        int arcHeight = (int) (height * 0.25);

        // Anti-aliasing for smooth corners
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color
        // g2.setColor(new Color(80, 80, 80)); // Custom background

        Color color = switch (level) {
            case 1 -> new Color(220, 237, 200); // Very light green
            case 2 -> new Color(198, 228, 139);
            case 3 -> new Color(174, 221, 119);
            case 4 -> new Color(152, 214, 101);
            case 5 -> new Color(132, 208, 91);
            case 6 -> new Color(123, 201, 111);
            case 7 -> new Color(95, 186, 95);
            case 8 -> new Color(72, 169, 78);
            case 9 -> new Color(56, 161, 69);
            case 10 -> new Color(43, 154, 61);
            case 11 -> new Color(35, 154, 59);
            case 12 -> new Color(30, 130, 48);
            case 13 -> new Color(25, 112, 43);
            case 14 -> new Color(25, 97, 39); // Darkest
            default -> new Color(64, 64, 64, transparency);
        };

        g2.setColor(color);
        g2.fillRoundRect(0, 0, width, height, arcWidth, arcHeight);

        super.paintComponent(g2);
        g2.dispose();
    }

    // Setters
    public void setLevel(int level){
        this.level = level;
    }

    // Getters
    public int getLevel(){
        return level;
    }
}
