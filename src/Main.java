import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new VaultConsole());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}