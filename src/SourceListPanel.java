import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SourceListPanel extends RoundedPanel {
    
    private JTextField searchBar;
    private DefaultTableModel passwordsModel;
    private JTable passwordVault;
    
    public SourceListPanel() {
        super(25, new Color(248, 250, 252));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(250, 520));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel findPassword = new JLabel("Find Password", JLabel.LEFT);
        findPassword.setForeground(new Color(30, 41, 59));
        findPassword.setFont(new Font("Sans Serif", Font.BOLD, 18));
        
        JPanel searchBarHolder = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        searchBarHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBarHolder.setOpaque(false);
        searchBarHolder.setMaximumSize(new Dimension(230, 30));
        
        JLabel search = new JLabel("Search:", JLabel.LEFT);
        search.setForeground(new Color(30, 41, 59));
        search.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        searchBar = new JTextField(10);
        searchBar.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        
        searchBarHolder.add(search);
        searchBarHolder.add(searchBar);
        
        JLabel passVaultLabel = new JLabel("Password Vault", JLabel.LEFT);
        passVaultLabel.setForeground(new Color(30, 41, 59));
        passVaultLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        String[] columnName = {"Site Name"};
        String[][][] test = {{
            {"Github"},
            {"Google"},
            {"Netflix"},
            {"Steam"},
            {"Epic Games"},
            {"Amazon"},
            {"Target"}
        }};
        
        passwordsModel = new DefaultTableModel(test, columnName) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        passwordVault = new JTable(passwordsModel);
        passwordVault.setTableHeader(null);
        passwordVault.setShowGrid(true);
        passwordVault.setShowVerticalLines(false);
        passwordVault.setGridColor(new Color(226, 232, 240));
        pass
        
        JScrollPane scroller = new JScrollPane(passwordVault);
        scroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        
        add(findPassword);
        add(Box.createVerticalStrut(50));
        add(searchBarHolder);
        add(Box.createVerticalStrut(40));
        add(passVaultLabel);
        add(Box.createVerticalStrut(5));
        add(scroller);
        
    }
}
