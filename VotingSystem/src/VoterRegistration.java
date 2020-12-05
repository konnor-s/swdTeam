import javax.swing.*;
import java.awt.*;

public class VoterRegistration extends JFrame {
    VoterRegistration(){
        super("Voter Registration");
        JLabel license = new JLabel("Drivers License Number");
        JTextField licenseT = new JTextField();
        JLabel name = new JLabel("First and Last Name");
        JTextField nameT = new JTextField();
        JLabel address = new JLabel("Address");
        JTextField addressT = new JTextField();
        JLabel county = new JLabel("County");
        JTextField countyT = new JTextField();
        JLabel state = new JLabel("State");
        JTextField stateT = new JTextField();

        setLayout(new GridLayout(6,2));
        add(license);
        add(licenseT);
        add(name);
        add(nameT);
        add(address);
        add(addressT);
        add(county);
        add(countyT);
        add(state);
        add(stateT);

        JButton reg = new JButton("Click to Register");
        add(reg);

    }
}
