import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
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
        File vaultFile = new File("vault.enc");
        
        JFrame frame = new JFrame();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new OnWindowClose());
        
        masterPassword = SetupDialog.showGateway(frame);
        if(masterPassword == null || masterPassword.length == 0) {
            System.exit(5);
        }
        if(vaultFile.exists()) {
            readEnc();
        }
        
        frame.add(new VaultConsole(passVault));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    class OnWindowClose implements WindowListener {
        public void windowClosing(WindowEvent evt) {
            System.exit(0);
        }
        
        public void windowOpened(WindowEvent evt) {}
        public void windowClosed(WindowEvent evt) {}
        public void windowIconified(WindowEvent evt) {}
        public void windowDeiconified(WindowEvent evt) {}
        public void windowActivated(WindowEvent evt) {}
        public void windowDeactivated(WindowEvent evt) {}
    }
    
    public void readEnc()  {
        try(DataInputStream reader = new DataInputStream(new FileInputStream("vault.enc"))) {
            masterPassword = readArray(reader);
            
            int numEntries = reader.readInt();
            for(int i = 0; i < numEntries; i++) {
                passVault.addEntry(readArray(reader), readArray(reader),
                    readArray(reader), readArray(reader));
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot safely " +
                "read from \"vault.enc\".", "File Access Error",
                JOptionPane.PLAIN_MESSAGE, null);
        }
    }
    
    public char[] readArray(DataInputStream reader) throws IOException {
        byte[] bytes = new byte[reader.readInt()];
        reader.readFully(bytes);
        
        CharBuffer chars = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes));
        char[] data = new char[chars.remaining()];
        chars.get(data);
        Arrays.fill(bytes, (byte)(0));
        
        return data;
    }
    
    public void writeEnc() {
        try(DataOutputStream writer = new DataOutputStream(new FileOutputStream("vault.enc"))){
            writeArray(writer, masterPassword);
            writer.writeInt(passVault.numEntries());
            
            for(int i = 0; i < passVault.numEntries(); i++) {
                VaultEntry currEntry = passVault.getEntry(i);
                
                writeArray(writer, currEntry.getSite());
                writeArray(writer, currEntry.getUsername());
                writeArray(writer, currEntry.getPassword());
                writeArray(writer, currEntry.getUrl());
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot" +
                " safely write to \"vault.enc\".","File Creation Error",
                JOptionPane.PLAIN_MESSAGE, null);
        }
    }
    
    public void writeArray(DataOutputStream writer, char[] array) throws IOException {
        ByteBuffer bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(array));
        byte[] rawData = new byte[bytes.remaining()];
        bytes.get(rawData);
        
        writer.writeInt(rawData.length);
        writer.write(rawData);
        
        Arrays.fill(rawData, (byte)(0));
    }
}