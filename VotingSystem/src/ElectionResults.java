import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;

public class ElectionResults extends JFrame{
    private ArrayList<String> choices;
    private ArrayList<String> positions;
    private ArrayList<String> counties;
    private ArrayList<String> states;
    private ArrayList<Integer> votes;

    public ElectionResults(){
        super("Election Results");
        //setLayout(new FlowLayout());

        String[] regions = {"Select Region", "All States", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        JComboBox regionBox = new JComboBox<String>(regions);
        add(regionBox, BorderLayout.NORTH);
        regionBox.addItemListener(
                new ItemListener(){
                    @Override
                    public void itemStateChanged(ItemEvent event){
                        if(event.getStateChange() == ItemEvent.SELECTED) {
                            String str = String.valueOf(regionBox.getSelectedItem());
                            System.out.println(str);
                        }
                    }
                }
        );

    }

    public void getResults(){
        final String SELECT_QUERY = "SELECT Choice, Position, County, State, Votes FROM Ballot";
        choices = new ArrayList<>();
        positions = new ArrayList<>();
        counties = new ArrayList<>();
        states = new ArrayList<>();
        votes = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class011", "engr_class011", "dbforece!");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (resultSet.next()){
                choices.add(String.valueOf(resultSet.getObject(1)));
                positions.add(String.valueOf(resultSet.getObject(2)));
                counties.add(String.valueOf(resultSet.getObject(3)));
                states.add(String.valueOf(resultSet.getObject(4)));
                votes.add((int)resultSet.getObject(5));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stateSelected(String state){
        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
    }
}
