import javax.swing.*;
import java.awt.*;

public class VaultConsole extends JPanel {
    
    public VaultConsole() {
        setBackground(new Color(15, 23, 42));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        RoundedPanel titleHolder = new RoundedPanel(25, new Color(235, 235, 235));
        titleHolder.setPreferredSize(new Dimension(880, 50));
        titleHolder.setBackground(new Color(248, 250, 252));
        titleHolder.setLayout(new BoxLayout(titleHolder, BoxLayout.X_AXIS));
        titleHolder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel title = new JLabel("Aegis Password Vault", JLabel.LEFT);
        title.setBackground(getBackground());
        title.setForeground(new Color(30, 41, 59));
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        titleHolder.add(title);
        
        add(titleHolder, BorderLayout.NORTH);
        add(new SourceListPanel(), BorderLayout.WEST);
        add(new DetailViewPanel(), BorderLayout.EAST);
    }
}
