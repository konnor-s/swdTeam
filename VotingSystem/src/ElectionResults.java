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
    private JPanel selectionPanel;
    private JComboBox positionBox;
    private String state;
    private String position;

    public ElectionResults(){
        super("Election Results");
        getData();
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        add(selectionPanel, BorderLayout.NORTH);


        String[] regions = {"Select Region", "All States", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        JComboBox regionBox = new JComboBox(regions);
        selectionPanel.add(regionBox);
        positionBox = new JComboBox();
        selectionPanel.add(positionBox);
        regionBox.addItemListener(
                new ItemListener(){
                    @Override
                    public void itemStateChanged(ItemEvent event){
                        if(event.getStateChange() == ItemEvent.SELECTED) {
                            state = String.valueOf(regionBox.getSelectedItem());
                            stateSelected();
                        }
                    }
                }
        );
        positionBox.addItemListener(
                new ItemListener(){
                    @Override
                    public void itemStateChanged(ItemEvent event){
                        if(event.getStateChange() == ItemEvent.SELECTED) {
                            position = String.valueOf(positionBox.getSelectedItem());
                            getResults();
                        }
                    }
                }
        );


    }

    public void getData(){
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
                votes.add(resultSet.getInt(5));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stateSelected(){

        ArrayList<String> list = new ArrayList<>();
        // get list of all positions in state
        for(int i = 0; i < states.size(); i++){
            if(states.get(i).equals(state)){
                list.add(positions.get(i));
            }
        }
        // Remove duplicates
        ArrayList<String> statePositions = new ArrayList<>();
        for(int i = 0; i < list.size(); i ++){
            if(!statePositions.contains(list.get(i))){
                statePositions.add(list.get(i));
            }
        }
        if(positionBox.getItemCount()>0){
            positionBox.removeAllItems();
        }
        for(int i = 0; i < statePositions.size(); i ++){
            positionBox.addItem(statePositions.get(i));
        }

    }

    public void getResults(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < votes.size(); i++){
            if(states.get(i).equals(state) && positions.get(i).equals(position)){
                list.add(choices.get(i));
            }
        }
        ArrayList<String> candidates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(!candidates.contains(list.get(i))){
                candidates.add(list.get(i));
            }
        }
        int[] numVotes = new int[candidates.size()];
        for(int i = 0; i < votes.size(); i++) {
            if (states.get(i).equals(state) && positions.get(i).equals(position)) {
                for (int j = 0; j < candidates.size(); j++) {
                    if(candidates.get(j).equals(choices.get(i))){
                        numVotes[j] += votes.get(i);
                    }

                }
            }
        }
        for(int i = 0; i < candidates.size(); i++){
            System.out.println(candidates.get(i) + " " + numVotes[i]);
        }
    }
}
