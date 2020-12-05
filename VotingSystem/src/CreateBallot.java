import javax.swing.*;

public class CreateBallot extends JFrame {
    CreateBallot(String county, String state){
        super("Create Ballot for "+county+", "+ state);
        //if this table doesn't exist, create it
        //TextField1: Type in name of position eg. President
        //JTextField2: Type in name of candidate
        //JButton: submit name and position to database to create new entry. JTextField2 auto clears for new entry, JTextField1 stays the same.
        //JButton: remove name and position from database
        //JButton: preview current ballot

    }

}
