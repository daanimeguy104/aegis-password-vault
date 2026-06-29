import javax.swing.*;
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
        File vaultFile = new File("vault.txt.enc");
        
        JFrame frame = new JFrame();
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
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
    
    public void readEnc()  {
        DataInputStream reader = null;
        
        try {
            reader = new DataInputStream(new FileInputStream("vault.txt.enc"));
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Cannot find" +
                " \"vault.txt.enc\" to read from.", "File Access Error",
                JOptionPane.PLAIN_MESSAGE, null);
            return;
        }
        
        try {
            masterPassword = readArray(reader);
            
            int numEntries = reader.readInt();
            for(int i = 0; i < numEntries; i++) {
                passVault.addEntry(readArray(reader), readArray(reader),
                    readArray(reader), readArray(reader));
            }
            reader.close();
        } catch(IOException e) {
        
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
        DataOutputStream writer = null;
        
        try {
            writer = new DataOutputStream(new FileOutputStream(("vault.txt.enc")));
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Cannot create" +
                    " \"vault.txt.enc\" to write to.", "File Creation Error",
                JOptionPane.PLAIN_MESSAGE, null);
            return;
        }
        
        try {
            writeArray(writer, masterPassword);
            writer.writeInt(passVault.numEntries());
            
            for(int i = 0; i < passVault.numEntries(); i++) {
                VaultEntry currEntry = passVault.getEntry(i);
                
                writeArray(writer, currEntry.getSite());
                writeArray(writer, currEntry.getUsername());
                writeArray(writer, currEntry.getPassword());
                writeArray(writer, currEntry.getUrl());
            }
            writer.close();
        } catch(IOException e) {
        
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