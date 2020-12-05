import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Ballot extends JFrame {
    Ballot(String county, String state){
        super("Voter Ballot for "+county+", "+ state);

        int p =9 //should be number of positions from database.

        setLayout(new GridLayout(1,2));
        JPanel lPanel = new JPanel();
        JPanel rPanel = new JPanel();
        lPanel.setLayout(new FlowLayout());
        rPanel.setLayout(new FlowLayout());

        ArrayList positions = new ArrayList<ButtonGroup>();
        //for(String votePositions in database positions category){


            //create new JPanel in either left or right column
            if(i<=Math.ceil(p/2))){
                lPanel.add(new JPanel())
}
            else{
                rPanel.add(new JPanel());
             }
            //create button group
            //

            //for(int l = 0; l<p;l++){
                //create button for this candidate.
                //add button to button group.
                //add button to panel
            //add panel to frame

    }
}
