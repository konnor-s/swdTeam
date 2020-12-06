import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * The CreateBallot.java class is responsible for putting the candidates onto the ballot. When an auditor logs in,
 * they will have the options to: add candidate to ballot, remove candidate from ballot, preview the current ballot, and
 * finalize the votes. This is done via 4 JButtons with ActionListeners. Inside of the ActionListeners, the database is updated
 * respective to the button that is clicked.
 */
public class CreateBallot extends JFrame {
    CreateBallot(String county, String state){
        super("Create Ballot for "+county+", "+ state);
        setLayout(new GridLayout(17,20,20,20));

        //Creating GUI objects
        JTextField jtfForPosition = new JTextField("Enter title of position");
        JTextField jtfForName = new JTextField("Enter name of candidate");
        JButton jb1 = new JButton("Submit to database");
        JButton jb2 = new JButton("Remove from database");
        JButton jb3 = new JButton("Preview current ballot for "  + county + ", " + state);
        JButton jb4 = new JButton("Click to finalize results for " + county + ", " + state);

        //Submit name & position to database to add onto the ballot
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
                    //Check if this candidate is already in the database
                    for (Object name : allChoices) {
                        if (jtfForName.getText().equals(name)) {
                            exists = true;
                        }
                    }

                    //If it doesn't exist, they get added to the database
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
                        //Display a pop up menu to tell user the candidate is already in the database
                        JPopupMenu temp = new JPopupMenu("Error");
                        temp.add(new JLabel("This person is already a candidate"));
                        temp.setVisible(true);
                        temp.setSize(300,300);

                        Timer t = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                               temp.setVisible(false);
                            }
                        });
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

        //This button when clicked will remove a candidate from the database.
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    //Connect to server
                    Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");

                    //Removing from the database
                    String tempString = "DELETE FROM Ballot WHERE Choice = ? AND County = ?";
                    PreparedStatement remove = connection.prepareStatement(tempString);
                    remove.setString(1,jtfForName.getText());
                    remove.setString(2,county);
                    remove.executeUpdate();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });

        //Preview Ballot for current County and State
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Creates a Ballot object so it can be previewed for respective county in state
                Ballot previewBallot = new Ballot(county, state,false,"");
                previewBallot.setVisible(true);
                previewBallot.setSize(1200,800);
                previewBallot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        //For finalizing votes - when clicked it will set finalized column in database to be a 1 indicated the
        //results are final.
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    //Connect to server
                    Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");

                    //Updating Finalized column in Ballot
                    String tempString = "UPDATE Ballot SET Finalized = ? WHERE County = ? AND State = ?";
                    PreparedStatement upd = connection.prepareStatement(tempString);
                    upd.setInt(1,1);
                    upd.setString(2,county);
                    upd.setString(3,state);
                    upd.executeUpdate();
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
        add(jb3);
        add(jb4);
    }
}
