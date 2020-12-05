import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateBallot extends JFrame {
    CreateBallot(String county, String state){
        super("Create Ballot for "+county+", "+ state);
        setLayout(new FlowLayout());


        JTextField jtf1 = new JTextField("Enter title of position");
        JTextField jtf2 = new JTextField("Enter name of candidate");
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



                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

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

        add(jtf1);
        add(jb1);
        add(jtf2);
        add(jb2);


        //if this table doesn't exist, create it
        //TextField1: Type in name of position eg. President
        //JTextField2: Type in name of candidate
        //JButton: submit name and position to database to create new entry. JTextField2 auto clears for new entry, JTextField1 stays the same.
        //JButton: remove name and position from database
        //JButton: preview current ballot

    }

}
