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
    private JComboBox countyBox;
    private String state;
    private String position;
    private String county;
    private JTextArea resultsArea;

    public ElectionResults(){
        super("Election Results");
        getData();
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        add(selectionPanel, BorderLayout.NORTH);
        /*
        String[] regions = {"Select Region", "All States", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas",
                "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina",
                "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

         */
        ArrayList<String> regions = new ArrayList<>();
        regions.add("Select Region");
        regions.add("All States");
        for(int i = 0; i < states.size(); i++){
            if(!regions.contains(states.get(i))){
                regions.add(states.get(i));
            }
        }
        JComboBox regionBox = new JComboBox();
        for(int i = 0; i < regions.size(); i++){
            regionBox.addItem(regions.get(i));
        }
        selectionPanel.add(regionBox);
        positionBox = new JComboBox();
        selectionPanel.add(positionBox);
        countyBox = new JComboBox();
        selectionPanel.add(countyBox);
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
        countyBox.addItemListener(
                new ItemListener(){
                    @Override
                    public void itemStateChanged(ItemEvent event){
                        if(event.getStateChange() == ItemEvent.SELECTED) {
                            county = String.valueOf(countyBox.getSelectedItem());
                            getResults();
                        }
                    }
                }
        );

        resultsArea = new JTextArea();
        add(resultsArea, BorderLayout.CENTER);

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

        ArrayList<String> posList = new ArrayList<>();
        ArrayList<String> countyList = new ArrayList<>();
        // get list of all positions in state
        for(int i = 0; i < states.size(); i++){
            if(states.get(i).equals(state)){
                posList.add(positions.get(i));
                countyList.add(counties.get(i));
            }
        }
        // Remove duplicates
        ArrayList<String> posList1 = new ArrayList<>();
        ArrayList<String> countyList1 = new ArrayList<>();
        posList1.add("Select Position");
        countyList1.add("Select County");
        countyList1.add("All Counties");
        for(int i = 0; i < posList.size(); i ++){
            if(!posList1.contains(posList.get(i))){
                posList1.add(posList.get(i));
            }
            if(!countyList1.contains(countyList.get(i))){
                countyList1.add(countyList.get(i));
            }
        }
        if(positionBox.getItemCount()>0){
            positionBox.removeAllItems();
        }
        for(int i = 0; i < posList1.size(); i ++){
            positionBox.addItem(posList1.get(i));
        }
        if(countyBox.getItemCount()>0){
            countyBox.removeAllItems();
        }
        for(int i = 0; i < countyList1.size(); i++){
            countyBox.addItem(countyList1.get(i));
        }

    }

    public void getResults(){
        if(state.equals("All States")){

        }
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
        double totalVotes = 0;
        for(int i = 0; i < votes.size(); i++) {
            if (states.get(i).equals(state) && positions.get(i).equals(position)) {
                totalVotes += votes.get(i);
                for (int j = 0; j < candidates.size(); j++) {
                    if(candidates.get(j).equals(choices.get(i))){
                        numVotes[j] += votes.get(i);
                    }

                }
            }
        }
        String text = "Candidates         Votes         Percentage\n";
        for(int i = 0; i < candidates.size(); i++){
            text += candidates.get(i) + "        " + numVotes[i] + "\n";
        }
        resultsArea.setText(text);
    }
}
