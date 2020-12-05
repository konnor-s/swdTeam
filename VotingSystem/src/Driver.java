import javax.swing.*;

public class Driver {
    public static void main(String args[]) {
        PrimaryInterface gui = new PrimaryInterface();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(1200, 800);
        gui.setVisible(true);


    }
}
