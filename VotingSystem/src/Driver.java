import javax.swing.*;

/**
 * Driver class for this project - Creates PrimaryInterface object when ran
 */
public class Driver {
    public static void main(String args[]) {
        PrimaryInterface gui = new PrimaryInterface();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(1200, 400);
        gui.setVisible(true);
    }
}
