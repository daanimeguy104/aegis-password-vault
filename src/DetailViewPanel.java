    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.Arrays;
    
    public class DetailViewPanel extends RoundedPanel {
        
        private SourceListPanel sourceLst;
        private State currState;
        private Vault passVault;
        
        private JLabel headerLabel;
        private SecurePasswordField siteTF;
        private SecurePasswordField urlTF;
        private SecurePasswordField userTF;
        private SecurePasswordField passTF;
        private JCheckBox showPassCB;
        
        private RoundedButton edit;
        private RoundedButton delete;
        private RoundedButton confirm;
        private RoundedButton cancel;
        
        public DetailViewPanel() {
            super(25, new Color(248, 250, 252));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            setPreferredSize(new Dimension(470, 520));
            setLayout(new BorderLayout(20, 20));
            
            currState = State.VIEW;
            
            headerLabel = new JLabel("Vault Entry Details");
            headerLabel.setFont(new Font("Sans Serif", Font.BOLD, 22));
            headerLabel.setForeground(new Color(30, 41, 59));
            
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.setOpaque(false);
            
            JLabel siteLabel = new JLabel("Site Name:", JLabel.LEFT);
            siteLabel.setForeground(new Color(30, 41, 59));
            siteLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            siteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            siteTF = new SecurePasswordField();
            siteTF.setEchoChar((char)(0));
            siteTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            siteTF.setForeground(new Color(30, 41, 59));
            siteTF.setMaximumSize(new Dimension(440, 35));
            siteTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            siteTF.setEditable(false);
            
            JLabel userLabel = new JLabel("Username:", JLabel.LEFT);
            userLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            userLabel.setForeground(new Color(30, 41, 59));
            userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            userTF = new SecurePasswordField();
            userTF.setEchoChar((char)(0));
            userTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            userTF.setForeground(new Color(30, 41, 59));
            userTF.setMaximumSize(new Dimension(440, 35));
            userTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            userTF.setEditable(false);
            
            JLabel passLabel = new JLabel("Password:", JLabel.LEFT);
            passLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            passLabel.setForeground(new Color(30, 41, 59));
            passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            passTF = new SecurePasswordField();
            passTF.setEchoChar('✲');
            passTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            passTF.setForeground(new Color(30, 41, 59));
            passTF.setMaximumSize(new Dimension(440, 35));
            passTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            passTF.setEditable(false);
            
            showPassCB = new JCheckBox("Show Password");
            showPassCB.setFont(new Font("Sans Serif", Font.BOLD, 13));
            showPassCB.setAlignmentX(Component.LEFT_ALIGNMENT);
            showPassCB.addActionListener(new ShowPassword());
            
            JLabel urlLabel = new JLabel("Site Url:", JLabel.LEFT);
            urlLabel.setForeground(new Color(30, 41, 59));
            urlLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            urlTF = new SecurePasswordField();
            urlTF.setEchoChar((char)(0));
            urlTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            urlTF.setForeground(new Color(30, 41, 59));
            urlTF.setMaximumSize(new Dimension(440, 35));
            urlTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            urlTF.setEditable(false);
            
            formPanel.add(siteLabel);
            formPanel.add(Box.createVerticalStrut(3));
            formPanel.add(siteTF);
            formPanel.add(Box.createVerticalStrut(22));
            
            formPanel.add(userLabel);
            formPanel.add(Box.createVerticalStrut(3));
            formPanel.add(userTF);
            formPanel.add(Box.createVerticalStrut(22));
            
            formPanel.add(passLabel);
            formPanel.add(Box.createVerticalStrut(3));
            formPanel.add(passTF);
            formPanel.add(Box.createVerticalStrut(3));
            formPanel.add(showPassCB);
            formPanel.add(Box.createVerticalStrut(22));
            
            formPanel.add(urlLabel);
            formPanel.add(Box.createVerticalStrut(3));
            formPanel.add(urlTF);
            formPanel.add(Box.createVerticalStrut(22));
            
            JPanel buttonPanel = new JPanel(new CardLayout());
            buttonPanel.setOpaque(false);
            buttonPanel.setPreferredSize(new Dimension(440, 75));
            
            JPanel viewMode = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            viewMode.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            viewMode.setOpaque(false);
            
            edit = new RoundedButton("Edit Selected Entry");
            edit.setBackground(new Color(37, 99, 235));
            edit.setForeground(Color.WHITE);
            edit.setFont(new Font("Sans Serif", Font.BOLD, 14));
            edit.setPreferredSize(new Dimension(200, 35));
            edit.addActionListener(new EditMode());
            
            delete = new RoundedButton("Delete Selected Entry");
            delete.setBackground(new Color(220, 53, 69));
            delete.setForeground(Color.WHITE);
            delete.setFont(new Font("Sans Serif", Font.BOLD, 14));
            delete.setPreferredSize(new Dimension(200, 35));
            
            viewMode.add(edit);
            viewMode.add(delete);
            
            JPanel changeMode = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            changeMode.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            changeMode.setOpaque(false);
            
            confirm = new RoundedButton("Save Changes");
            confirm.setBackground(new Color(40, 190, 69));
            confirm.setForeground(Color.WHITE);
            confirm.setFont(new Font("Sans Serif", Font.BOLD, 14));
            confirm.setPreferredSize(new Dimension(200, 35));
            confirm.addActionListener(new ConfirmAction());
            
            cancel = new RoundedButton("Cancel");
            cancel.setBackground(new Color(71, 85, 105));
            cancel.setForeground(Color.WHITE);
            cancel.setFont(new Font("Sans Serif", Font.BOLD, 14));
            cancel.setPreferredSize(new Dimension(200, 35));
            cancel.addActionListener(new CancelAction());
            
            changeMode.add(confirm);
            changeMode.add(cancel);
            
            buttonPanel.add(viewMode, "View");
            buttonPanel.add(changeMode, "Change");
            
            add(headerLabel, BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
            
            processState();
        }
        
        public void setState(State stateIn) {
            currState = stateIn;
            processState();
        }
        
        public State getState() {
            return currState;
        }
        
        public void setFields(SourceListPanel slp, Vault vaultIn) {
            sourceLst = slp;
            passVault = vaultIn;
        }
        
        class EditMode implements ActionListener {
            public void actionPerformed(ActionEvent evt) {
                currState = State.EDIT;
                processState();
                
                int currRow = sourceLst.getPasswordTable().getSelectedRow();
                
                if(currRow == -1) {
                    JOptionPane.showMessageDialog(DetailViewPanel.this,
                        "Please select a row.", "No Row Selected",
                        JOptionPane.PLAIN_MESSAGE, null);
                    return;
                }
                
                currRow = sourceLst.getPasswordTable().convertRowIndexToModel(currRow);
                VaultEntry currEntry = passVault.getEntry(currRow);
                populateTextFields(currEntry);
            }
        }
        
        class CancelAction implements ActionListener {
            public void actionPerformed(ActionEvent evt) {
                currState = State.VIEW;
                processState();
                sourceLst.showEntry();
            }
        }
        
        class ConfirmAction implements ActionListener {
            public void actionPerformed(ActionEvent evt) {
                char[] site = siteTF.getPassword();
                char[] username = userTF.getPassword();
                char[] password = passTF.getPassword();
                char[] url = urlTF.getPassword();
                
                if(isOnlySpace(site) || isOnlySpace(username) || isOnlySpace(password) ||
                    isOnlySpace(url)) {
                    
                    JOptionPane.showMessageDialog(DetailViewPanel.this,
                        "One or more input fields are empty.", "Missing Input",
                        JOptionPane.PLAIN_MESSAGE, null);
                    return;
                }
                
                if(currState == State.ADD) {
                    passVault.addEntry(site, username, password, url);
                    
                    DefaultTableModel passModel = sourceLst.getTableModel();
                    passModel.addRow(new Object[]{site.clone(), username.clone()});
                } else if(currState == State.EDIT) {
                    int currRow = sourceLst.getPasswordTable().getSelectedRow();
                    passVault.updateEntry(currRow, site, username, password, url);
                    
                    DefaultTableModel passModel = sourceLst.getTableModel();
                    passModel.setValueAt(site.clone(), currRow, 0);
                    passModel.setValueAt(username.clone(), currRow, 1);
                }
                
                Arrays.fill(site, '\0');
                Arrays.fill(username, '\0');
                Arrays.fill(password,'\0');
                Arrays.fill(url, '\0');
                
                clear();
                
                currState = State.VIEW;
                processState();
            }
        }
        
        class ShowPassword implements ActionListener {
            public void actionPerformed(ActionEvent evt) {
                if(showPassCB.isSelected()) {
                    passTF.setEchoChar((char)(0));
                } else {
                    passTF.setEchoChar('✲');
                }
            }
        }
        
        public void processState() {
            Container buttonPanel = (Container)(getComponent(2));
            CardLayout buttonCards = (CardLayout)(buttonPanel.getLayout());
            
            if(currState == State.VIEW) {
                setTFAccessabilty(false);
                headerLabel.setText("Vault Entry Details");
                buttonCards.show(buttonPanel, "View");
            } else if(currState == State.ADD) {
                setTFAccessabilty(true);
                confirm.setText("Add Entry");
                headerLabel.setText("New Vault Entry");
                buttonCards.show(buttonPanel, "Change");
            } else if(currState == State.EDIT) {
                setTFAccessabilty(true);
                confirm.setText("Save Changes");
                headerLabel.setText("Edit Selected Entry");
                buttonCards.show(buttonPanel, "Change");
            }
        }
        
        public void setTFAccessabilty(boolean isAccessible) {
            siteTF.setEditable(isAccessible);
            userTF.setEditable(isAccessible);
            passTF.setEditable(isAccessible);
            urlTF.setEditable(isAccessible);
        }
        
        public void clear() {
            siteTF.clearSecurely();
            userTF.clearSecurely();
            passTF.clearSecurely();
            urlTF.clearSecurely();
            showPassCB.setSelected(false);
        }
        
        public boolean isOnlySpace(char[] toCheck) {
            if(toCheck.length == 0 || toCheck == null) {
                return true;
            }
            
            for(int i = 0; i < toCheck.length; i++) {
                if(!Character.isWhitespace(toCheck[i])) {
                    return false;
                }
            }
            
            return true;
        }
        
        public void populateTextFields(VaultEntry currEntry) {
            if(currState == State.VIEW) {
                setTFAccessabilty(true);
            }
            
            siteTF.setTextChars(currEntry.getSite());
            userTF.setTextChars(currEntry.getUsername());
            passTF.setTextChars(currEntry.getPassword());
            urlTF.setTextChars(currEntry.getUrl());
            
            if(currState == State.VIEW) {
                setTFAccessabilty(false);
            }
        }
        
    }
