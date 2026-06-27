import java.util.ArrayList;
import java.util.Arrays;

public class Vault {

    private ArrayList<VaultEntry> vaultEntries;
    
    public Vault() {
        vaultEntries = new ArrayList<VaultEntry>();
    }
    
    public void addEntry(char[] site, char[] usernm, char[] passwrd, char[] url) {
        VaultEntry currEntry = new VaultEntry(site, usernm, passwrd, url);
        vaultEntries.add(currEntry);
    }
    
    public void removeEntry(int index) {
        vaultEntries.remove(index);
    }
    
    public void updateEntry(int index, char[] site, char[] usernm, char[] passwrd, char[] url) {
        VaultEntry changed = new VaultEntry(site, usernm, passwrd, url);
        vaultEntries.set(index, changed).wipe();
    }
    
    public VaultEntry getEntry(int index) {
        return vaultEntries.get(index);
    }
    
    public void wipeVault() {
        for(int i = 0; i < vaultEntries.size(); i++) {
            vaultEntries.get(i).wipe();
        }
    }
    
    public String toString() {
        return vaultEntries.toString();
    }
}
