    import javax.swing.*;
    import java.awt.*;
    
    public class DetailViewPanel extends RoundedPanel {
        
        private JLabel headerLabel;
        private JTextField siteTF;
        private JTextField urlTF;
        private JTextField userTF;
        private JPasswordField passTF;
        private JCheckBox showPassCB;
        private RoundedButton edit;
        private RoundedButton delete;
        private RoundedButton save;
        private RoundedButton cancel;
        
        public DetailViewPanel() {
            super(25, new Color(248, 250, 252));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            setPreferredSize(new Dimension(470, 520));
            setLayout(new BorderLayout(20, 20));
            
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
            
            siteTF = new JTextField();
            siteTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            siteTF.setForeground(new Color(30, 41, 59));
            siteTF.setMaximumSize(new Dimension(440, 35));
            siteTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel userLabel = new JLabel("Username:", JLabel.LEFT);
            userLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            userLabel.setForeground(new Color(30, 41, 59));
            userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            userTF = new JTextField();
            userTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            userTF.setForeground(new Color(30, 41, 59));
            userTF.setMaximumSize(new Dimension(440, 35));
            userTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel passLabel = new JLabel("Password:", JLabel.LEFT);
            passLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            passLabel.setForeground(new Color(30, 41, 59));
            passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            passTF = new JPasswordField();
            passTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            passTF.setForeground(new Color(30, 41, 59));
            passTF.setMaximumSize(new Dimension(440, 35));
            passTF.setEchoChar('*');
            passTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            showPassCB = new JCheckBox("Show Password");
            showPassCB.setFont(new Font("Sans Serif", Font.BOLD, 13));
            showPassCB.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel urlLabel = new JLabel("Site Url:", JLabel.LEFT);
            urlLabel.setForeground(new Color(30, 41, 59));
            urlLabel.setFont(new Font("Sans Serif", Font.BOLD, 17));
            urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            urlTF = new JTextField();
            urlTF.setFont(new Font("Sans Serif", Font.PLAIN, 17));
            urlTF.setForeground(new Color(30, 41, 59));
            urlTF.setMaximumSize(new Dimension(440, 35));
            urlTF.setAlignmentX(Component.LEFT_ALIGNMENT);
            
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
            
            save = new RoundedButton("Save Entry");
            save.setBackground(new Color(40, 190, 69));
            save.setForeground(Color.WHITE);
            save.setFont(new Font("Sans Serif", Font.BOLD, 14));
            save.setPreferredSize(new Dimension(200, 35));
            
            cancel = new RoundedButton("Cancel");
            cancel.setBackground(new Color(71, 85, 105));
            cancel.setForeground(Color.WHITE);
            cancel.setFont(new Font("Sans Serif", Font.BOLD, 14));
            cancel.setPreferredSize(new Dimension(200, 35));
            
            changeMode.add(save);
            changeMode.add(cancel);
            
            buttonPanel.add(viewMode, "View");
            buttonPanel.add(changeMode, "Change");
            
            add(headerLabel, BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
