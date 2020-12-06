import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class which creates a ballot gui for voting for candidates.
 * This ballot is particular to this county and state, and will display each position in the database, with an option for each choice for that position.
 * It will check if the voter has voted already before submitting, and will mark a voter as having voted when they submit.
 */
public class Ballot extends JFrame {
    Ballot(String county, String state, boolean votable, String vID){
        super("Voter Ballot for "+county+", "+ state);

        setLayout(new GridLayout(20,2,5,1));


        ArrayList groups = new ArrayList<ButtonGroup>();//arraylist that holds ButtonGroups of each position
        ArrayList arrayOfChoices = new ArrayList<ArrayList>();//arraylist that holds arraylists of choices per position
        ArrayList arrayOfButtons = new ArrayList<ArrayList>();//arraylist that holds arraylists of buttons per position corresponding to arrayOfChoices

        try {
            //Connect to server
            Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
            Statement statement = connection.createStatement();

            //Get list of positions in this county
            ResultSet rs = statement.executeQuery("SELECT Position FROM Ballot WHERE County = '" + county + "' AND State = '" + state + "'");
            ArrayList pos = new ArrayList<String>();
            while (rs.next()) {
                if (!pos.contains(rs.getString(1))) {
                    pos.add(rs.getString(1));
                }
            }

            //loop through the list of positions
            for (int i = 0; i < pos.size(); i++) {
                groups.add(new ButtonGroup());
                ArrayList choices = new ArrayList<String>();
                ArrayList buttons = new ArrayList<JButton>();
                JPanel posPanel = new JPanel();
                posPanel.setLayout(new GridLayout(1,3));
                posPanel.add(new JPanel());
                posPanel.add(new JLabel((String) pos.get(i)));
                posPanel.add(new JPanel());
                add(posPanel);

                //get list of choice for this position
                rs = statement.executeQuery("SELECT Choice FROM Ballot WHERE Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state + "'");
                while (rs.next()) {
                    choices.add(rs.getString(1));
                }

                //create labels and buttons for this position
                for (int j = 0; j < choices.size(); j++) {
                    buttons.add(new JRadioButton());
                    JLabel l = new JLabel((String) choices.get(j));
                    JPanel p = new JPanel();
                    p.add((JRadioButton) buttons.get(j));
                    p.add(l);
                    add(p);

                    ((ButtonGroup) groups.get(i)).add((JRadioButton) buttons.get(j));

                }
                //Add the arrays of choices and buttons for this position to the array of arrays
                arrayOfChoices.add(choices);
                arrayOfButtons.add(buttons);
            }
            //If this ballot is able to be voted on (not a preview)
            if (votable) {
                //Make submit button
                JButton submit = new JButton("Submit Votes");
                add(submit);
                submit.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                try{
                                    //Check if this person has already voted
                                    boolean voted = true;
                                    ResultSet rs1 = statement.executeQuery("SELECT Voted FROM VoterRegistry WHERE License = '" + vID + "' AND County = '" + county + "' AND State = '" + state + "'");
                                    rs1.next();
                                    voted = rs1.getBoolean(1);


                                    if (!voted) {
                                        //For each button on the ballot, check if it is selected
                                        for (int i = 0; i < arrayOfButtons.size(); i++) {
                                            for (int j = 0; j < ((ArrayList) (arrayOfButtons.get(i))).size(); j++) {
                                                if (((JRadioButton) ((ArrayList) (arrayOfButtons.get(i))).get(j)).isSelected()) {
                                                    //obtain the choice of the selected button
                                                    String choice = (String) ((ArrayList) arrayOfChoices.get(i)).get(j);

                                                    //cast the vote for this button
                                                    ResultSet rs = statement.executeQuery("SELECT Votes FROM Ballot WHERE Choice = '" + choice + "' AND Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state + "'");
                                                    rs.next();
                                                    int votes = rs.getInt(1) + 1;
                                                    Statement statement = connection.createStatement();
                                                    statement.execute("UPDATE Ballot SET Votes = '" + votes + "' WHERE Choice = '" + choice + "' AND Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state + "'");
                                                }
                                            }
                                        }
                                        //count this person as having voted
                                        Statement statement2 = connection.createStatement();
                                        statement2.execute("UPDATE VoterRegistry SET Voted = '1' WHERE License = '" + vID + "' AND County = '"+ county + "' AND State = '"+state+"'");
                                    }

                                }
                                catch(SQLException e){
                                    e.printStackTrace();
                                }
                                Ballot.super.dispose();
                            }
                        }
                );


            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Ballot.super.dispose();

    }
}
