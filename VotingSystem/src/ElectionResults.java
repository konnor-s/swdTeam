import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class for displaying election results
 */
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
        getData(); // get data from database
        county = "";
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        add(selectionPanel, BorderLayout.NORTH);

        // Get list of states with certified results
        ArrayList<String> regions = new ArrayList<>();
        regions.add("Select Region");
        regions.add("All States");
        for(int i = 0; i < states.size(); i++){
            if(!regions.contains(states.get(i))){
                regions.add(states.get(i));
            }
        }
        // Add list of regions to JComboBox
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
                    /**
                     * Method called when item selected in regionBox.
                     * Sets state and calls stateSelected
                     * @param event ItemEvent
                     */
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
                    /**
                     * Method called when item selected in positionBox.
                     * Sets position and calls getResults
                     * @param event ItemEvent
                     */
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
                    /**
                     * Method called when item selected in countyBox.
                     * Sets county and calls getResults
                     * @param event ItemEvent
                     */
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

    /**
     * Method connects to database to get results and stores data in ArrayLists
     */
    public void getData(){
        final String SELECT_QUERY = "SELECT Choice, Position, County, State, Votes, Certified FROM Ballot";
        choices = new ArrayList<>();
        positions = new ArrayList<>();
        counties = new ArrayList<>();
        states = new ArrayList<>();
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
                if(resultSet.getInt(6) == 1){
                    choices.add(resultSet.getString(1));
                    positions.add(resultSet.getString(2));
                    counties.add(resultSet.getString(3));
                    states.add(resultSet.getString(4));
                    votes.add(resultSet.getInt(5));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when state is selected in regionBox. Determine what positions/counties have results and sets position and county JComboBoxes with options.
     */
    public void stateSelected(){

        ArrayList<String> posList = new ArrayList<>();
        ArrayList<String> countyList = new ArrayList<>();
        ArrayList<String> posList1 = new ArrayList<>();
        ArrayList<String> countyList1 = new ArrayList<>();
        posList1.add("Select Position");

        // If All States selected add all positions to list
        if(state.equals("All States")){
            for(int i = 0; i < states.size(); i++){
                if(!posList1.contains(positions.get(i))){
                    posList1.add(positions.get(i));
                }
            }
        }
        // else add all positions and counties with results to list if in state selected
        else{
            for(int i = 0; i < states.size(); i++){
                if(states.get(i).equals(state)){
                    posList.add(positions.get(i));
                    countyList.add(counties.get(i));
                }
            }
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
        }

        // Remove items from JComboBoxes then fill with new options
        positionBox.removeAllItems();
        for(int i = 0; i < posList1.size(); i ++){
            positionBox.addItem(posList1.get(i));
        }

        countyBox.removeAllItems();
        for(int i = 0; i < countyList1.size(); i++){
            countyBox.addItem(countyList1.get(i));
        }

    }

    /**
     * Method counts votes for location and position of interest and prints results of election
     */
    public void getResults(){
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> candidates = new ArrayList<>();

        // Get list of candidates on ballots
        if(state.equals("All States")){
            for(int i = 0; i < votes.size(); i++){
                if(positions.get(i).equals(position)){
                    list.add(choices.get(i));
                }
            }
        }else if(county.equals("All Counties")){
            for(int i = 0; i < votes.size(); i++){
                if(states.get(i).equals(state) && positions.get(i).equals(position)){
                    list.add(choices.get(i));
                }
            }
        }else{
            for(int i = 0; i < votes.size(); i++){
                if(states.get(i).equals(state) && positions.get(i).equals(position) && counties.get(i).equals(county)){
                    list.add(choices.get(i));
                }
            }
        }
        // Remove duplicates from list by creating new arraylist of candidates
        for(int i = 0; i < list.size(); i++){
            if(!candidates.contains(list.get(i))){
                candidates.add(list.get(i));
            }
        }
        int[] numVotes = new int[candidates.size()]; // array to store number of votes for each candidate
        double totalVotes = 0; // count total number of votes
        // Loop through data and determine number of votes for each candidate
        if(state.equals("All States")){
            for(int i = 0; i < votes.size(); i++){
                if(positions.get(i).equals(position)){
                    totalVotes += votes.get(i);
                    for (int j = 0; j < candidates.size(); j++) {
                        if(candidates.get(j).equals(choices.get(i))){
                            numVotes[j] += votes.get(i);
                        }
                    }
                }
            }
        }else if(county.equals("All Counties")){
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
        }else{
            for(int i = 0; i < votes.size(); i++) {
                if (states.get(i).equals(state) && positions.get(i).equals(position) && counties.get(i).equals(county)) {
                    totalVotes += votes.get(i);
                    for (int j = 0; j < candidates.size(); j++) {
                        if(candidates.get(j).equals(choices.get(i))){
                            numVotes[j] += votes.get(i);
                        }
                    }
                }
            }
        }
        // Calculate percent of votes for each candidate
        double[] percentages = new double[candidates.size()];
        if(totalVotes != 0){ // make sure to avoid division by zero
            for(int i = 0; i < candidates.size(); i++){
                percentages[i] = numVotes[i]/totalVotes*100;
            }
        }

        // Create string of results
        String text = "Candidates         Votes         Percentage\n";
        for(int i = 0; i < candidates.size(); i++){
            text += candidates.get(i) + "        " + numVotes[i] + "            " + percentages[i] + "%\n";
        }
        resultsArea.setText(text); // set text area with results
    }
}
