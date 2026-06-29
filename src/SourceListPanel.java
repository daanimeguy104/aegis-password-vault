import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SourceListPanel extends RoundedPanel {
    
    private DetailViewPanel detailVw;
    private Vault passVault;
    
    private JPasswordField searchBar;
    private DefaultTableModel passwordsModel;
    private JTable passwordVault;
    private TableRowSorter<DefaultTableModel> vaultSorter;
    private RoundedButton addPassword;
    
    public SourceListPanel() {
        super(25, new Color(248, 250, 252));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(400, 520));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel findPassword = new JLabel("Locate Vault Entry", JLabel.LEFT);
        findPassword.setForeground(new Color(30, 41, 59));
        findPassword.setFont(new Font("Sans Serif", Font.BOLD, 22));
        
        JPanel searchBarHolder = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        searchBarHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBarHolder.setOpaque(false);
        searchBarHolder.setMaximumSize(new Dimension(270, 30));
        
        JLabel search = new JLabel("Search:", JLabel.LEFT);
        search.setForeground(new Color(30, 41, 59));
        search.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        searchBar = new JPasswordField(14);
        searchBar.setEchoChar((char)(0));
        searchBar.putClientProperty("JPasswordField.cutCopyAllowed", true);
        searchBar.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        searchBar.setForeground(new Color(30, 41, 59));
        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchBar.getDocument().addDocumentListener(new FindEntry());
        
        searchBarHolder.add(search);
        searchBarHolder.add(searchBar);
        
        JLabel passVaultLabel = new JLabel("Password Vault", JLabel.LEFT);
        passVaultLabel.setForeground(new Color(30, 41, 59));
        passVaultLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        
        JPanel passVaultWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passVaultWrapper.setOpaque(false);
        passVaultWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] columnName = {"Site Name", "Username"};
        
        passwordsModel = new DefaultTableModel(columnName, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vaultSorter = new TableRowSorter<DefaultTableModel>(passwordsModel);
        
        passwordVault = new JTable(passwordsModel);
        passwordVault.getSelectionModel().addListSelectionListener(new SelectedRow());
        passwordVault.setRowSorter(vaultSorter);
        passwordVault.setShowGrid(true);
        passwordVault.setShowVerticalLines(false);
        passwordVault.setGridColor(new Color(226, 232, 240));
        passwordVault.setRowHeight(40);
        passwordVault.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passwordVault.setSelectionBackground(new Color(0, 0, 0, 0));
        passwordVault.setFillsViewportHeight(true);
        passwordVault.setOpaque(false);
        passwordVault.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        passwordVault.getTableHeader().setFont(new Font("Sans Serif", Font.BOLD, 14));
        passwordVault.getTableHeader().setBackground(getBackground());
        passwordVault.getTableHeader().setForeground(new Color(30, 41, 59));
        passwordVault.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        passwordVault.getTableHeader().setReorderingAllowed(false);
        passwordVault.getTableHeader().setResizingAllowed(false);
        passwordVault.getColumnModel().getColumn(0).setMaxWidth(120);
        passwordVault.getColumnModel().getColumn(0).setMinWidth(120);
        
        passwordVault.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            
            private boolean isRowSelected;
            private char[] cellVal;
            
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                
                isRowSelected = isSelected;
                
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setText("");
                cellVal =  (char[])(value);
                
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
                Graphics2D g2d = (Graphics2D) (g.create());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if(isRowSelected) {
                    g2d.setColor(new Color(37, 99, 235));
                    g2d.fillRoundRect(4, 2, getWidth() - 8, getHeight() - 4,
                        12, 12);
                }
                
                super.paintComponent(g);
                
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                
                FontMetrics fm = g2d.getFontMetrics();
                int x = 15;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                
                g2d.drawChars(cellVal, 0, cellVal.length, x, y);
                g2d.dispose();
            }
        });
        
        JScrollPane scroller = new JScrollPane(passwordVault);
        scroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setMaximumSize(new Dimension(370, 220));
        scroller.setMinimumSize(new Dimension(370, 220));
        scroller.setPreferredSize(new Dimension(370, 220));
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        
        addPassword = new RoundedButton("Create New Vault Entry");
        addPassword.setBackground(new Color(37, 99, 235));
        addPassword.setFont(new Font("Sans Serif", Font.BOLD, 13));
        addPassword.setForeground(Color.WHITE);
        addPassword.addActionListener(new AddMode());
        
        passVaultWrapper.add(scroller);
        add(findPassword);
        add(Box.createVerticalStrut(50));
        add(searchBarHolder);
        add(Box.createVerticalStrut(40));
        add(passVaultLabel);
        add(Box.createVerticalStrut(10));
        add(passVaultWrapper);
        add(Box.createVerticalStrut(15));
        add(addPassword);
        add(Box.createVerticalGlue());
    }
    
    public void setFields(DetailViewPanel dvp, Vault vaultIn) {
        detailVw = dvp;
        passVault = vaultIn;
        
        for(int i = 0; i < passVault.numEntries(); i++) {
            VaultEntry currEntry = passVault.getEntry(i);
            
            passwordsModel.addRow(new Object[]{currEntry.getSite().clone(),
                currEntry.getUsername().clone()});
        }
    }
    
    class AddMode implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            detailVw.setState(State.ADD);
            detailVw.processState();
            detailVw.clear();
        }
    }
    
    public DefaultTableModel getTableModel() {
        return passwordsModel;
    }
    
    public JTable getPasswordTable() {
        return passwordVault;
    }
    
    class SelectedRow implements ListSelectionListener {
        
        public void valueChanged(ListSelectionEvent evt) {
            if(!evt.getValueIsAdjusting()) {
                showEntry();
            }
        }
    }
    
    class FindEntry implements DocumentListener {
        public void insertUpdate(DocumentEvent evt) {
            applyFilter();
        }
        
        public void removeUpdate(DocumentEvent evt) {
            applyFilter();
        }
        
        public void changedUpdate(DocumentEvent evt) {
            applyFilter();
        }
        
        public void applyFilter() {
            char[] query = searchBar.getPassword();
            
            if(detailVw.isOnlySpace(query)) {
                vaultSorter.setRowFilter(null);
                return;
            }
            
            vaultSorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                public boolean include(Entry<? extends DefaultTableModel, ?
                    extends Integer> entry) {
                    
                    int currRow = entry.getIdentifier();
                    char[] site = ((char[])(passwordsModel.getValueAt(currRow, 0))).clone();
                    
                    if(site.length < query.length) {
                        Arrays.fill(site, '\0');
                        return false;
                    }
                    
                    for(int i = 0; i <= site.length - query.length; i++) {
                        int j = 0;
                        for(j = 0; j < query.length; j++) {
                            char siteChar = Character.toLowerCase(site[i + j]);
                            char queryChar = Character.toLowerCase(query[j]);
                            
                            if(siteChar != queryChar) {
                                break;
                            }
                        }
                        
                        if(j == query.length) {
                            Arrays.fill(site, '\0');
                            return true;
                        }
                    }
                    
                    Arrays.fill(site, '\0');
                    return false;
                }
            });
            
            Arrays.fill(query, '\0');
        }
    }
    
    public void showEntry() {
        int currRow = passwordVault.getSelectedRow();
        
        if(currRow != -1 && detailVw.getState() != State.ADD) {
            currRow = passwordVault.convertRowIndexToModel(currRow);
            
            VaultEntry currEntry = passVault.getEntry(currRow);
            detailVw.processState();
            detailVw.populateTextFields(currEntry);
        }
    }
}
