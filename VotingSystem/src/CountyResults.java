import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class CountyResults extends JFrame {
    private String county;
    private String state;
    private ArrayList<String> choices;
    private ArrayList<String> positions;
    private ArrayList<Integer> votes;

    public CountyResults(String county, String state){
        super("Current Results for " + county + ", " + state);
        this.county = county;
        this.state = state;
        getData();
        setLayout(new GridLayout(choices.size()+1,3));
        add(new JLabel("Position"));
        add(new JLabel("Choice"));
        add(new JLabel("Votes"));
        for(int i = 0; i < choices.size(); i++){
            add(new JLabel(positions.get(i)));
            add(new JLabel(choices.get(i)));
            add(new JLabel(String.valueOf(votes.get(i))));
        }
    }

    public void getData(){
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
