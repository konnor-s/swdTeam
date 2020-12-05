import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

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
        JLabel message = new JLabel("");
        add(message);

        reg.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //Make sure fields are filled out
                        if (licenseT.getText().equals("") || name.getText().equals("") || addressT.getText().equals("") || countyT.getText().equals("") || stateT.getText().equals("")) {
                            message.setText("All fields must be filled out!");
                        }
                        else {
                            try {
                                //Connect to server
                                Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                                Statement statement = connection.createStatement();

                                //Get list of license numbers in the registry
                                ResultSet rs = statement.executeQuery("SELECT License FROM VoterRegistry");
                                ArrayList ids = new ArrayList<String>();
                                while (rs.next()) {
                                    ids.add(rs.getString(1));
                                }
                                boolean exists = false;
                                //Check if this license is already in the database
                                for (Object id : ids) {
                                    if (licenseT.getText().equals(id)) {
                                        exists = true;
                                    }
                                }
                                //Create a new entry in the database if this is a new voter
                                if (!exists) {
                                    PreparedStatement insert = connection.prepareStatement("INSERT INTO VoterRegistry " + "(License, name,address,county,state) " + "VALUES (?,?,?,?,?)");
                                    insert.setString(1, licenseT.getText());
                                    insert.setString(2, nameT.getText());
                                    insert.setString(3, addressT.getText());
                                    insert.setString(4, countyT.getText());
                                    insert.setString(5, state.getText());
                                    insert.executeUpdate();
                                }
                                else {
                                    message.setText("This license is already registered!");
                                }
                                connection.close();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            } ;
                        }
                    }
                }
        );

    }
}
