import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    
    private int cornerRadius;
    
    public RoundedButton(String text) {
       this(text, 15);
    }
    
    public RoundedButton(String text, int cornerRadius) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        this.cornerRadius = cornerRadius;
    }
    
    
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)(g.create());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2d.dispose();
        
        super.paintComponent(g);
    }
}
