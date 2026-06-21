public class VaultEntry {
    
    private String siteName;
    private String url;
    private String username;
    private String password;
    
    public VaultEntry(String siteIn, String urlIn, String userIn, String passIn) {
        siteName = siteIn;
        url = urlIn;
        username = userIn;
        password = passIn;
    }
    
    public String getSite() {
        return siteName;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
}
