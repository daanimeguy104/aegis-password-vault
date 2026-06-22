import javax.swing.*;
import java.awt.*;

public class DetailViewPanel extends RoundedPanel {
    
    public DetailViewPanel() {
        super(25, new Color(248, 250, 252));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(620, 520));
        setLayout(new BorderLayout());
    }
}
