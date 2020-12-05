import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CreateBallot extends JFrame {
    CreateBallot(String county, String state){
        super("Create Ballot for "+county+", "+ state);
        setLayout(new FlowLayout());

        JTextField jtfForPosition = new JTextField("Enter title of position");
        JTextField jtfForName = new JTextField("Enter name of candidate");
        JButton jb1 = new JButton("Submit to database");
        JButton jb2 = new JButton("Remove from database");

        //Submit name & position to database
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    //Connect to server
                    Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                    Statement statement = connection.createStatement();

                    //Get list of positions in the registry
                    ResultSet rs = statement.executeQuery("SELECT Choice FROM Ballot");
                    ArrayList allChoices = new ArrayList<String>();
                    while (rs.next()) {
                        allChoices.add(rs.getString(1));
                    }
                    boolean exists = false;
                    //Check if this license is already in the database
                    for (Object name : allChoices) {
                        if (jtfForName.getText().equals(name)) {
                            exists = true;
                        }
                    }

                    if (!exists){
                        PreparedStatement insert = connection.prepareStatement("INSERT INTO Ballot " + "(Choice,County,State,Position,Votes) " + "VALUES (?,?,?,?,?)");
                        insert.setString(1,jtfForName.getText());
                        insert.setString(2,county);
                        insert.setString(3,state);
                        insert.setString(4,jtfForPosition.getText());
                        insert.setInt(5,0);
                        insert.executeUpdate();
                    }
                    else{
                        JPopupMenu temp = new JPopupMenu("Error");
                        temp.add(new JLabel("This person is already a candidate"));
                        temp.setVisible(true);
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

        //REMOVE FROM DATABASE
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    //Connect to server
                    Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                    Statement statement = connection.createStatement();


                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

        add(jtfForName);
        add(jtfForPosition);
        add(jb1);
        add(jb2);


        //if this table doesn't exist, create it
        //TextField1: Type in name of position eg. President
        //JTextField2: Type in name of candidate
        //JButton: submit name and position to database to create new entry. JTextField2 auto clears for new entry, JTextField1 stays the same.
        //JButton: remove name and position from database
        //JButton: preview current ballot

    }

}
