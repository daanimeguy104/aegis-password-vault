import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        
        JPanel passVaultWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passVaultWrapper.setOpaque(false);
        passVaultWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] columnName = {"Site Name"};
        String[][] test = {
            {"Github"},
            {"Google"},
            {"Netflix"},
            {"Steam"},
            {"Epic Games"},
            {"Amazon"},
            {"Target"},
            {"Github"},
            {"Google"},
            {"Netflix"},
            {"Steam"},
            {"Epic Games"},
            {"Amazon"},
            {"Target"}
        };
        
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
        passwordVault.setRowHeight(40);
        passwordVault.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passwordVault.setSelectionBackground(new Color(0, 0, 0, 0));
        passwordVault.setFillsViewportHeight(true);
        passwordVault.setOpaque(false);
        
        passwordVault.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
            private boolean isRowSelected;
            
            public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                isRowSelected = isSelected;
                
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
                setFont(new Font("Sans Serif", Font.PLAIN, 14));
                
                if(isSelected) {
                    setBackground(new Color(0, 0, 0, 0));
                    setForeground(Color.WHITE);
                } else {
                    setForeground(new Color(30, 41, 59));
                    
                    if(row % 2 == 0) {
                        setBackground(new Color(248, 250, 252));
                    } else {
                        setBackground(new Color(241, 245, 249));
                    }
                }
                
                return this;
            }
            
            protected void paintComponent(Graphics g) {
                if(isRowSelected) {
                    Graphics2D g2d = (Graphics2D) (g.create());
                    
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(79, 70, 229));
                    g2d.fillRoundRect(4, 2, getWidth() - 8, getHeight() - 4,
                        12, 12);
                    g2d.dispose();
                }
                
                super.paintComponent(g);
            }
        });
        
        JScrollPane scroller = new JScrollPane(passwordVault);
        scroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setMaximumSize(new Dimension(230, 240));
        scroller.setMinimumSize(new Dimension(230, 240));
        scroller.setPreferredSize(new Dimension(230, 240));
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        
        passVaultWrapper.add(scroller);
        add(findPassword);
        add(Box.createVerticalStrut(50));
        add(searchBarHolder);
        add(Box.createVerticalStrut(40));
        add(passVaultLabel);
        add(Box.createVerticalStrut(5));
        add(passVaultWrapper);
        add(Box.createVerticalGlue());
        
    }
}
