import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;

public class SetupDialog extends JDialog {
    
    private SecurePasswordField passField;
    private SecurePasswordField confirmPass;
    private RoundedButton enter;
    private JCheckBox showPass;
    
    private boolean needsSetup;
    private char[] masterPass;
    
    public SetupDialog(JFrame parent, boolean needsSetup) {
        super(parent, "", true);
        this.needsSetup = needsSetup;
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());
        
        JPanel holderPanel = new RoundedPanel(25, new Color(248, 250, 252));
        holderPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        holderPanel.setLayout(new BoxLayout(holderPanel, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Aegis Password Vault");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(new Font("Sans Serif", Font.BOLD, 18));
        title.setForeground(new Color(30, 41, 59));
        
        JLabel enterPass = new JLabel("Enter Master Password:");
        enterPass.setForeground(new Color(30, 41, 59));
        enterPass.setFont(new Font("Sans Serif", Font.BOLD, 15));
        enterPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passField = new SecurePasswordField();
        passField.setEchoChar('•');
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField.setForeground(new Color(30, 41, 59));
        passField.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        passField.setMaximumSize(new Dimension(800, 30));
        
        JLabel confirmPassLbl = new JLabel("Confirm Master Password:");
        confirmPassLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPassLbl.setForeground(new Color(30, 41, 59));
        confirmPassLbl.setFont(new Font("Sans Serif", Font.BOLD, 15));
        
        confirmPass = new SecurePasswordField();
        confirmPass.setEchoChar('•');
        confirmPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPass.setForeground(new Color(30, 41, 59));
        confirmPass.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        confirmPass.setMaximumSize(new Dimension(800, 30));
        
        showPass = new JCheckBox("Show password");
        showPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPass.setForeground(new Color(30, 41, 59));
        showPass.setFont(new Font("Sans Serif", Font.BOLD, 12));
        showPass.addActionListener(new ShowPassword());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        enter = new RoundedButton();
        enter.setBackground(new Color(37, 99, 235));
        enter.setFont(new Font("Sans Serif", Font.BOLD, 13));
        enter.setForeground(Color.WHITE);
        enter.addActionListener(new ConfirmEntry());
        
        buttonWrapper.add(enter);
        
        holderPanel.add(title);
        holderPanel.add(Box.createVerticalStrut(30));
        holderPanel.add(enterPass);
        holderPanel.add(Box.createVerticalStrut(3));
        holderPanel.add(passField);
        holderPanel.add(Box.createVerticalStrut(15));
        holderPanel.add(confirmPassLbl);
        holderPanel.add(Box.createVerticalStrut(3));
        holderPanel.add(confirmPass);
        holderPanel.add(Box.createVerticalStrut(5));
        holderPanel.add(showPass);
        holderPanel.add(Box.createVerticalStrut(50));
        holderPanel.add(buttonWrapper);
        
        add(holderPanel);
        
        if(needsSetup) {
            enter.setText("Create Vault");
            confirmPassLbl.setVisible(true);
            confirmPass.setVisible(true);
            setSize(new Dimension(360, 320));
        } else {
            enter.setText("Unlock Vault");
            confirmPassLbl.setVisible(false);
            confirmPass.setVisible(false);
            setSize(new Dimension(360, 270));
        }
        setLocationRelativeTo(null);
    }
    
    public static char[] showGateway(JFrame parent) {
        File vaultFile = new File("vault.txt.enc");
        
        SetupDialog setup = new SetupDialog(parent, !vaultFile.exists());
        setup.setVisible(true);
        
        char[] masterCopy = setup.masterPass;
        setup.masterPass = null;
        
        return masterCopy;
    }
    
    class ConfirmEntry implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if(needsSetup) {
                char[] password = passField.getPassword();
                char[] confirmPassword = confirmPass.getPassword();
                
                if(isOnlyWhitespace(password) || isOnlyWhitespace(confirmPassword)) {
                    JOptionPane.showMessageDialog(SetupDialog.this, "One" +
                        " or more input fields are empty.", "Missing Input",
                        JOptionPane.PLAIN_MESSAGE, null);
                    return;
                }
                
                if(!Arrays.equals(password, confirmPassword)) {
                    JOptionPane.showMessageDialog(SetupDialog.this,
                        "Passwords don't match", "Invalid Input",
                        JOptionPane.PLAIN_MESSAGE, null);
                    return;
                }
                
                masterPass = password.clone();
                
                Arrays.fill(password, '\0');
                Arrays.fill(confirmPassword, '\0');
                
                dispose();
            }
        }
    }
    
    class ShowPassword implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if(showPass.isSelected()) {
                passField.setEchoChar((char)(0));
                confirmPass.setEchoChar((char)(0));
                
                passField.putClientProperty("JPasswordField.cutCopyAllowed", true);
                confirmPass.putClientProperty("JPasswordField.cutCopyAllowed", true);
            } else {
                passField.setEchoChar('•');
                confirmPass.setEchoChar('•');
                
                passField.putClientProperty("JPasswordField.cutCopyAllowed", false);
                passField.putClientProperty("JPasswordField.cutCopyAllowed", false);
            }
        }
    }
    
    public boolean isOnlyWhitespace(char[] toCheck) {
        if(toCheck == null || toCheck.length == 0) {
            return true;
        }
        
        for(int i = 0; i < toCheck.length; i++) {
            if(!Character.isWhitespace(toCheck[i])) {
                return false;
            }
        }
        
        return true;
    }
}
