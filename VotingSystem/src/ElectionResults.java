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
        county = "";
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        add(selectionPanel, BorderLayout.NORTH);

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
        ArrayList<String> posList1 = new ArrayList<>();
        ArrayList<String> countyList1 = new ArrayList<>();
        posList1.add("Select Position");


        // Get list of positions and counties
        if(state.equals("All States")){
            for(int i = 0; i < states.size(); i++){
                if(!posList1.contains(positions.get(i))){
                    posList1.add(positions.get(i));
                }
            }
        }else{
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


        positionBox.removeAllItems();
        for(int i = 0; i < posList1.size(); i ++){
            positionBox.addItem(posList1.get(i));
        }

        countyBox.removeAllItems();
        for(int i = 0; i < countyList1.size(); i++){
            countyBox.addItem(countyList1.get(i));
        }

    }

    public void getResults(){
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> candidates = new ArrayList<>();


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

        for(int i = 0; i < list.size(); i++){
            if(!candidates.contains(list.get(i))){
                candidates.add(list.get(i));
            }
        }
        int[] numVotes = new int[candidates.size()];
        double totalVotes = 0;
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
        double[] percentages = new double[candidates.size()];
        if(totalVotes != 0){
            for(int i = 0; i < candidates.size(); i++){
                percentages[i] = numVotes[i]/totalVotes*100;
            }
        }

        String text = "Candidates         Votes         Percentage\n";
        for(int i = 0; i < candidates.size(); i++){
            text += candidates.get(i) + "        " + numVotes[i] + "            " + percentages[i] + "%\n";
        }
        resultsArea.setText(text);
    }
}
