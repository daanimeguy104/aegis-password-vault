public class VaultEntry {
    
    private char[] siteName;
    private char[] username;
    private char[] password;
    private char[] url;
    
    public VaultEntry(char[] siteIn, char[] userIn, char[] passIn, char[] urlIn) {
        siteName = siteIn;
        username = userIn;
        password = passIn;
        url = urlIn;
    }
    
    public char[] getSite() {
        return siteName;
    }
    
    public char[] getUsername() {
        return username;
    }
    
    public char[] getPassword() {
        return password;
    }
    
    public char[] getUrl() {
        return url;
    }
    
    public void wipe() {
        for(int i = 0; i < siteName.length; i++) {
            siteName[i] = '\0';
        }
        
        for(int i = 0; i < username.length; i++) {
            username[i] = '\0';
        }
        
        for(int i = 0; i < password.length; i++) {
            password[i] = '\0';
        }
        
        for(int i = 0; i < url.length; i++) {
            url[i] = '\0';
        }
    }
}
