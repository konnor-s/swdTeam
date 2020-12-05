import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ElectionResults extends JFrame{

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

    public void stateSelected(String state){
        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
    }
}
