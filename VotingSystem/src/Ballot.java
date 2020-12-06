import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Ballot extends JFrame {
    Ballot(String county, String state, boolean votable, String vID){
        super("Voter Ballot for "+county+", "+ state);


        setLayout(new GridLayout(20,2,5,1));
        JPanel lPanel = new JPanel();
        JPanel rPanel = new JPanel();
        lPanel.setLayout(new FlowLayout());
        rPanel.setLayout(new FlowLayout());

        ArrayList groups = new ArrayList<ButtonGroup>();

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

                for (int j = 0; j < choices.size(); j++) {
                    buttons.add(new JRadioButton());
                    JLabel l = new JLabel((String) choices.get(j));
                    JPanel p = new JPanel();
                    p.add((JRadioButton) buttons.get(j));
                    p.add(l);
                    add(p);

                    ((ButtonGroup) groups.get(i)).add((JRadioButton) buttons.get(j));

                }
                arrayOfChoices.add(choices);
                arrayOfButtons.add(buttons);
            }
            if (votable) {
                JButton submit = new JButton("Submit Votes");
                add(submit);
                submit.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                for (int i = 0; i < arrayOfButtons.size(); i++) {
                                    for (int j = 0; j < ((ArrayList) (arrayOfButtons.get(i))).size(); j++) {
                                        if (((JRadioButton) ((ArrayList) (arrayOfButtons.get(i))).get(j)).isSelected()) {
                                            String choice = (String) ((ArrayList) arrayOfChoices.get(i)).get(j);
                                            try {
                                                //Check if this person has already voted
                                                boolean voted = true;
                                                ResultSet rs1 = statement.executeQuery("SELECT Voted FROM VoterRegistry WHERE License = '" + vID + "' AND County = '" + county + "' AND State = '" + state + "'");
                                                rs1.next();
                                                voted = rs1.getBoolean(1);

                                                if(!voted) {
                                                    ResultSet rs = statement.executeQuery("SELECT Votes FROM Ballot WHERE Choice = '" + choice + "' AND Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state + "'");
                                                    rs.next();
                                                    int votes = rs.getInt(1) + 1;
                                                    Statement statement = connection.createStatement();
                                                    statement.execute("UPDATE Ballot SET Votes = '" + votes + "' WHERE Choice = '" + choice + "' AND Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state + "'");

                                                    Statement statement2 = connection.createStatement();
                                                    statement2.execute("UPDATE VoterRegistry SET Voted = '1' WHERE License = '" + vID + "'");
                                                }


                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
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

    }
}
