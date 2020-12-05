import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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

        reg.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql:s-l112.engr.uiowa.edu:3306", "engr_class011", "dbforece!");
                            Statement statement = connection.createStatement();
                            //ResultSet ids = statement.executeQuery("SELECT License FROM VoterRegistry");

                            PreparedStatement insert = connection.prepareStatement("INSERT INTO VoterRegistry "+"(License, name) "+"VALUES (?,?)");
                            insert.setString(1,"123456");
                            insert.setString(2,"Konnor");
                            insert.executeUpdate();

                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        } ;

                    }
                }
        );

    }
}
