package scoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Setting extends JDialog {

    
    public Setting() {
        // Set undecorated and size
        this.setUndecorated(true);
        this.setSize(500, 300);
        this.setLocation(1210, 180);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Rounded corner shape
        this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
        
        // Add custom painted panel
        this.add(new CustomPanel());
        
        // Show dialog
        this.setVisible(true);
    }
    
    class CustomPanel extends JPanel {
        private JLabel label;
        public CustomPanel() {
            this.setLayout(new BorderLayout());

            label = new JLabel("Try to add different settings in \'future\'", SwingConstants.CENTER);
            label.setForeground(Color.white);
            label.setFont(new Font("", Font.PLAIN, 20));

            this.setPreferredSize(new Dimension(500, 300));
            this.setBackground(new Color(50, 50, 50));

            this.add(label, BorderLayout.CENTER);

            this.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
        }
    }
}
