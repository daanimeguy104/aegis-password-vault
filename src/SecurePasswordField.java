import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyEvent;

public class SecurePasswordField extends JPasswordField {

    public SecurePasswordField() {
        super();
    }
    
    public void setTextChars(char[] data) {
        clearSecurely();
        
        for(int i = 0; i < data.length; i++) {
            KeyEvent currChar = new KeyEvent(this, KeyEvent.KEY_TYPED, System.currentTimeMillis(),
                0, KeyEvent.VK_UNDEFINED, data[i]);
            
            processKeyEvent(currChar);
        }
    }
    
    public void clearSecurely() {
        Document doc = this.getDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch(BadLocationException e) {
        
        }
    }
}
