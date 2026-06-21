import javax.swing.*;
import java.awt.*;

/**
 * A simple JPanel that paints a rounded-rectangle background. Useful as a lightweight
 * "card" with rounded corners.
 *
 * @author      Vishruth (daanimeguy104)
 * @since      June 10th, 2026
 */
public class RoundedPanel extends JPanel {
    
    /**
     * Radius (pixels) for the rounded corners.
     * */
    private int cornerRadius;
    
    /**
     * Create a RoundedPanel with the given corner radius and background color.
     *
     * @param cornerRadius      Corner curvature in pixels
     * @param bgColor           background color used when painting the panel
     */
    public RoundedPanel(int cornerRadius, Color bgColor) {
        this.cornerRadius = cornerRadius;
        setBackground(bgColor);
        setOpaque(false); // we paint our own background (rounded)
    }
    
    /**
     * Paints the rounded background using the panel's background color.
     *
     * @param g     Graphics context supplied by Swing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // keep default child/component painting behavior
        
        // use Graphics2D for smoother rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // fill a rounded rectangle that covers the component area
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}