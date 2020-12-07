import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Class CountyResults is responsible for keeping track of results by County.
 * Displays a GUI of the results gotten from the database.
 */
public class CountyResults extends JFrame {
    private String county;
    private String state;
    private ArrayList<String> choices;
    private ArrayList<String> positions;
    private ArrayList<Integer> votes;

    public CountyResults(String county, String state){
        //Setting up the GUI
        super("Current Results for " + county + ", " + state);
        this.county = county;
        this.state = state;
        getData();
        JPanel resultsPanel = new JPanel();
        add(resultsPanel, BorderLayout.CENTER);
        resultsPanel.setLayout(new GridLayout(choices.size()+1,3));
        resultsPanel.add(new JLabel("Position"));
        resultsPanel.add(new JLabel("Choice"));
        resultsPanel.add(new JLabel("Votes"));
        for(int i = 0; i < choices.size(); i++){
            resultsPanel.add(new JLabel(positions.get(i)));
            resultsPanel.add(new JLabel(choices.get(i)));
            resultsPanel.add(new JLabel(String.valueOf(votes.get(i))));
        }
        JButton certify = new JButton("Certify");
        certify.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try{
                            //Connect to server
                            Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");

                            //Updating Finalized column in Ballot
                            String tempString = "UPDATE Ballot SET Certified = ? WHERE County = ? AND State = ?";
                            PreparedStatement upd = connection.prepareStatement(tempString);
                            upd.setInt(1,1);
                            upd.setString(2,county);
                            upd.setString(3,state);
                            upd.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Results Certified");
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                        }

                    }
                }
        );
        add(certify, BorderLayout.SOUTH);
    }

    public void getData(){
        //Method which fills up these 3 ArrayLists so they can be displayed on the GUI
        final String SELECT_QUERY = "SELECT Choice, Position, County, State, Votes FROM Ballot";
        choices = new ArrayList<>();
        positions = new ArrayList<>();
        votes = new ArrayList<>();

        try (
                // connect to database
                Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                // create Statement for querying database
                Statement statement = connection.createStatement();
                // Query database
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()){
                if(resultSet.getString(3).equals(county) && resultSet.getString(4).equals(state)){
                    choices.add(resultSet.getString(1));
                    positions.add(resultSet.getString(2));
                    votes.add(resultSet.getInt(5));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
