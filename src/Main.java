import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
    
    private Vault passVault;
    private char[] masterPassword;
    
    public Main() {
        passVault = new Vault();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                main.writeEnc();
                main.passVault.delete();
                Arrays.fill(main.masterPassword, '\0');
            }
        });
    }
    
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        masterPassword = SetupDialog.showGateway(frame);
        if(masterPassword == null || masterPassword.length == 0) {
            System.exit(5);
        }
        
        frame.add(new VaultConsole(passVault));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void writeEnc() {
        PrintWriter output = null;
        
        try {
            output = new PrintWriter("vault.txt.enc");
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot create" +
                "\"vault.enc\" for writing to.", "File Creation Error",
                JOptionPane.PLAIN_MESSAGE, null);
            return;
        }
        
        writeArray(output, masterPassword);
        output.println('\n');
        
        output.println('[');
        for(int i = 0; i < passVault.numEntries(); i++) {
            VaultEntry currEntry = passVault.getEntry(i);
            
            
            output.println("    {");
            output.print("        \"siteName\": ");
            writeArray(output, currEntry.getSite());
            output.println(',');
            
            output.print("        \"username\": ");
            writeArray(output, currEntry.getUsername());
            output.println(',');
            
            output.print("        \"password\": ");
            writeArray(output, currEntry.getPassword());
            output.println(',');
            
            output.print("        \"url\": ");
            writeArray(output, currEntry.getUrl());
            output.println();
            
            output.print("    }");
            
            if(i != passVault.numEntries() - 1) {
                output.println(',');
            }
        }
        output.println("\n]");
        output.close();
    }
    
    public void writeArray(PrintWriter pw, char[] toWrite) {
        for(int i = 0; i < toWrite.length; i++) {
            pw.print(toWrite[i]);
        }
    }
    
}