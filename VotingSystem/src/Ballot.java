import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Ballot extends JFrame {
    Ballot(String county, String state){
        super("Voter Ballot for "+county+", "+ state);


        setLayout(new GridLayout(30,2));
        JPanel lPanel = new JPanel();
        JPanel rPanel = new JPanel();
        lPanel.setLayout(new FlowLayout());
        rPanel.setLayout(new FlowLayout());

        ArrayList groups = new ArrayList<ButtonGroup>();
        ArrayList arrayOfChoices = new ArrayList<ArrayList>();
        ArrayList arrayOfChoiceButtons = new ArrayList<ArrayList>();

        try {
            //Connect to server
            Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
            Statement statement = connection.createStatement();

            //Get list of positions in this county
            ResultSet rs = statement.executeQuery("SELECT Position FROM Ballot WHERE County = '" + county + "' AND State = '" + state+"'");
            ArrayList pos = new ArrayList<String>();
            while (rs.next()) {
                if (!pos.contains(rs.getString(1))) {
                    pos.add(rs.getString(1));
                }
            }
            int posLength = pos.size();



            for (int i = 0; i < posLength; i++) {
                groups.add(new ButtonGroup());
                ArrayList choices = new ArrayList<String>();
                ArrayList buttons = new ArrayList<JButton>();
                ArrayList labels = new ArrayList<JRadioButton>();
                ArrayList panels = new ArrayList<JPanel>();
                add(new JLabel((String) pos.get(i)));

                //get list of choice for this position
                rs = statement.executeQuery("SELECT Choice FROM Ballot WHERE Position ='" + pos.get(i) + "' AND County = '" + county + "' AND State = '" + state+"'");
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JButton submit = new JButton("Submit Votes");
        add(submit);
        submit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        for(Object b:arrayOfChoiceButtons){

                        }
                    }
                }
        );
    }
}
